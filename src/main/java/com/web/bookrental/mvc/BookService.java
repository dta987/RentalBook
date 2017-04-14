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

	// �������
	public int addBook(Book book) {
		return repository.insertBook(book);
	}

	// �̸������˻�
	public ArrayList<Book> searchBook(BookSearch booksearch) {

		Pageing pageing = new Pageing();

		booksearch.setBeginLow(pageing.getBeginRow(booksearch.getSelectPage()));
		booksearch.setEndRow(pageing.getendRow(booksearch.getSelectPage()));
		booksearch.setEndPage(
				pageing.getEndPage(searchName_totalPage(booksearch.getSearch_name(), pageing.getPageSize())));
		booksearch.setBeginPage(pageing.getBeginPage(booksearch.getSelectPage()));

		return repository.findByBookName(booksearch);
	}

	// �̸��˻����� ��
	public int searchName_totalPage(String search_name, int pageSize) {
		return (int) Math.ceil((double) repository.searchBookTotalCount(search_name) / pageSize);
	}

	// �����뿩
	public String rentalBook(int book_id) {

		String message;

		Book book = repository.findByBookId(book_id);

		if (book.getRental_check()) {
			message = "�뿩 ���� ���� �Դϴ�. \n�ݳ������ð�\n " + book.getReturn_schedule_time();
		} else {

			book = rentaltime(book);
			book.setRental_check(true);
			int cnt = repository.updatByRetalBook(book);
			message = "�뿩 �Ǿ����ϴ�. \n'" + book.getReturn_schedule_time() + "'\n���� �ݳ����ּ���";

		}

		return message;
	}

	// �뿩�ð��� �ݳ� �����ð�
	public Book rentaltime(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy�� MM�� dd�� HH:mm:ss");

		book.setRental_time(fm.format(cal.getTime()));

		cal.add(cal.SECOND, 10);

		book.setReturn_schedule_time(fm.format(cal.getTime()));

		return book;
	}

	// �����ݳ�
	public String returnBook(int book_id) {
		// �⺻�޽���
		String message = "�ݳ��Ǿ����ϴ�";

		// �ݳ� �������� ��ȸ
		Book book = repository.findByBookId(book_id);

		// ��ü���� �Ǵ��� ��üī��Ʈ ����
		int Over_time_count = book.getOver_time_count();

		// �ݳ��ð��� ��ü����
		book = returnBookCheck(book);

		// �ݳ����� ������Ʈ
		int cnt = repository.updateByReturnBook(book);

		// ��ü���� �Ǵ� Over_time_count�� �����Ǿ��ٸ� ��ü�� �Ǵ�
		if (Over_time_count < book.getOver_time_count()) {
			message = "�ݳ������� ���� �ݳ��Ǿ����ϴ�.";
		}

		return message;
	}

	// �ݳ��ð��� ��ü����
	public Book returnBookCheck(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy�� MM�� dd�� HH:mm:ss");

		try { // String�� Date�� ��ȯ �� ��ü���� �Ǵ�
				// ��ü�� //A.after(B) A�� ���� B�� �ð��� �� �����°�?
			if (cal.getTime().after(fm.parse(book.getReturn_schedule_time()))) {
				// ��üȽ�� ����
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

	// ���� �󼼺���
	public Book detailBook(int book_id) {
		return repository.findByBookId(book_id);
	}

	// �뿩���� ���� �˻�
	public ArrayList<Book> rentalBookSearch(BookSearch booksearch) {

		Pageing pageing = new Pageing();

		booksearch.setBeginLow(pageing.getBeginRow(booksearch.getSelectPage()));
		booksearch.setEndRow(pageing.getendRow(booksearch.getSelectPage()));
		booksearch.setEndPage(
				pageing.getEndPage(rentalBook_totalPage(booksearch.getSearch_name(), pageing.getPageSize())));
		booksearch.setBeginPage(pageing.getBeginPage(booksearch.getSelectPage()));

		return repository.findByRentalBookName(booksearch);
	}

	// �̸��˻����� ��
	public int rentalBook_totalPage(String search_name, int pageSize) {
		return (int) Math.ceil((double) repository.rental_totalCount(search_name) / pageSize);
	}

	// ��������
	public File statsBook(String file_name, String extension) {
		// �˻��̸� ���Ǽ���
		String search_name = "";
		// ���� ��ϰ�������
		ArrayList<Book> bookList = repository.findBySelectAll();

		Collections.sort(bookList, new Comparator<Book>() {
			@Override
			public int compare(Book o1, Book o2) {
				Integer num1 = o1.getBook_id();
				Integer num2 = o2.getBook_id();
				return num1.compareTo(num2);
			}
		});

	// ������������
	StatsFile statsfile = new StatsFile();statsfile.setFile_name(file_name);


	CreateFile createFile = new CreateFile();
	
	String[] head = { "ID", "������", "���ǻ�", "����", "����", "�뿩Ƚ��", "��üȽ��", "�뿩����", "�뿩�ð�", "�ݳ��ð�", "�ݳ������ð�" };
	
	File file = null;
	
	if(extension.equals("xlsx")) {
		file = createFile.createExcelFile(statsfile, bookList, head);
	} else if(extension.equals("text")) {
		file = createFile.createTextFile(statsfile, bookList);
	}
	
		return file;
	}

	

}
