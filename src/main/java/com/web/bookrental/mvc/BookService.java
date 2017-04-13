package com.web.bookrental.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.bookrental.dto.Book;
import com.web.bookrental.util.StatsFile;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	// 도서등록
	public int addBook(Book book) {

		/* book.setContext(book.getContext().replace("\n", "</br>")); */

		return repository.insertBook(book);
	}

	// 도서검색
	public ArrayList<Book> searchBook(String search_name) {
		return repository.findByBookName(search_name);
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
	public ArrayList<Book> rentalBookSearch(String search_name) {
		return repository.findByRentalBookName(search_name);
	}

	// 도서동계
	public File statsBook(String file_name) {
		// 검색이름 임의설정
		String search_name = "";
		// 도서 목록가져오기
		ArrayList<Book> bookList = repository.findByBookName(search_name);

		// 생성파일정보
		StatsFile statsfile = new StatsFile();
		statsfile.setFile_Name(file_name);

		File file = createFile(statsfile, bookList);

		return file;
	}

	public File createFile(StatsFile statsFile, ArrayList<Book> bookList) {

		File file = new File(statsFile.getFile_Real_Path());
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

			bw.write("ID\t도서명\t출판사\t저자\t가격\t대여횟수\t연체횟수\t대여여부\t대여시간\t\t\t반납시간");

			for (Book book : bookList) {
				String str = book.getBook_id() + "\t" + book.getBook_name() + "\t" + book.getCompany() + "\t"
						+ book.getWriter() + "\t" + book.getPrice() + "\t" + book.getRental_count() + "\t"
						+ book.getOver_time_count() + "\t";

				if (book.getRental_check()) {
					str += "대여중";
				} else {
					str += "대여가능";
				}

				str += "\t" + book.getRental_time() + "\t\t\t" + book.getReturn_time();

				bw.write(str);
				bw.newLine();
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;

	}

}
