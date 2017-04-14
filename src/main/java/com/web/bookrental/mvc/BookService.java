package com.web.bookrental.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.bookrental.dto.Book;
import com.web.bookrental.dto.BookSearch;
import com.web.bookrental.util.CreateFile;
import com.web.bookrental.util.Pageing;
import com.web.bookrental.util.StatsFile;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	// 도서등록
	public int addBook(Book book) {
		return repository.insertBook(book);
	}

	// 이름도서검색
	public ArrayList<Book> searchBook(BookSearch booksearch) {

		Pageing pageing = new Pageing();

		booksearch.setBeginLow(pageing.getBeginRow(booksearch.getSelectPage()));
		booksearch.setEndRow(pageing.getendRow(booksearch.getSelectPage()));
		booksearch.setEndPage(
				pageing.getEndPage(searchName_totalPage(booksearch.getSearch_name(), pageing.getPageSize())));
		booksearch.setBeginPage(pageing.getBeginPage(booksearch.getSelectPage()));

		return repository.findByBookName(booksearch);
	}

	// 이름검색도서 수
	public int searchName_totalPage(String search_name, int pageSize) {
		return (int) Math.ceil((double) repository.searchBookTotalCount(search_name) / pageSize);
	}

	// 도서대여
	public String rentalBook(int book_id) {

		String message;

		Book book = repository.findByBookId(book_id);

		if (book.getRental_check()) {
			message = "대여 중인 도서 입니다. \n반납예정시간\n " + book.getReturn_schedule_time();
		} else {

			book = rentaltime(book);
			book.setRental_check(true);
			int cnt = repository.updatByRetalBook(book);
			message = "대여 되었습니다. \n'" + book.getReturn_schedule_time() + "'\n까지 반납해주세요";

		}

		return message;
	}

	// 대여시간과 반납 예정시간
	public Book rentaltime(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");

		book.setRental_time(fm.format(cal.getTime()));

		cal.add(cal.SECOND, 10);

		book.setReturn_schedule_time(fm.format(cal.getTime()));

		return book;
	}

	// 도서반납
	public String returnBook(int book_id) {
		// 기본메시지
		String message = "반납되었습니다";

		// 반납 도서정보 조회
		Book book = repository.findByBookId(book_id);

		// 연체여부 판단전 연체카운트 저장
		int Over_time_count = book.getOver_time_count();

		// 반납시간과 연체여부
		book = returnBookCheck(book);

		// 반납정보 업데이트
		int cnt = repository.updateByReturnBook(book);

		// 연체여부 판단 Over_time_count가 증가되었다면 연체로 판단
		if (Over_time_count < book.getOver_time_count()) {
			message = "반납기한이 지나 반납되었습니다.";
		}

		return message;
	}

	// 반납시간과 연체여부
	public Book returnBookCheck(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");

		try { // String을 Date로 변환 후 연체여부 판단
				// 연체되 //A.after(B) A가 보다 B가 시간이 더 지났는가?
			if (cal.getTime().after(fm.parse(book.getReturn_schedule_time()))) {
				// 연체횟수 증가
				book.setOver_time_count(book.getOver_time_count() + 1);
				book.setReturn_time(fm.format(cal.getTime()));
				book.setRental_check(false);
			} else {
				book.setReturn_time(fm.format(cal.getTime()));
				book.setRental_check(false);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return book;
	}

	// 도서 상세보기
	public Book detailBook(int book_id) {
		return repository.findByBookId(book_id);
	}

	// 대여중인 도서 검색
	public ArrayList<Book> rentalBookSearch(BookSearch booksearch) {

		Pageing pageing = new Pageing();

		booksearch.setBeginLow(pageing.getBeginRow(booksearch.getSelectPage()));
		booksearch.setEndRow(pageing.getendRow(booksearch.getSelectPage()));
		booksearch.setEndPage(
				pageing.getEndPage(rentalBook_totalPage(booksearch.getSearch_name(), pageing.getPageSize())));
		booksearch.setBeginPage(pageing.getBeginPage(booksearch.getSelectPage()));

		return repository.findByRentalBookName(booksearch);
	}

	// 이름검색도서 수
	public int rentalBook_totalPage(String search_name, int pageSize) {
		return (int) Math.ceil((double) repository.rental_totalCount(search_name) / pageSize);
	}

	// 도서동계
	public File statsBook(String file_name, String extension) {
		// 검색이름 임의설정
		String search_name = "";
		// 도서 목록가져오기
		ArrayList<Book> bookList = repository.findBySelectAll();

		Collections.sort(bookList, new Comparator<Book>() {
			@Override
			public int compare(Book o1, Book o2) {
				Integer num1 = o1.getBook_id();
				Integer num2 = o2.getBook_id();
				return num1.compareTo(num2);
			}
		});

	// 생성파일정보
	StatsFile statsfile = new StatsFile();statsfile.setFile_name(file_name);


	CreateFile createFile = new CreateFile();
	
	String[] head = { "ID", "도서명", "출판사", "저자", "가격", "대여횟수", "연체횟수", "대여여부", "대여시간", "반납시간", "반납예정시간" };
	
	File file = null;
	
	if(extension.equals("xlsx")) {
		file = createFile.createExcelFile(statsfile, bookList, head);
	} else if(extension.equals("text")) {
		file = createFile.createTextFile(statsfile, bookList);
	}
	
		return file;
	}

	

}
