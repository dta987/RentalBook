package com.web.bookrental.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.bookrental.dto.Book;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	// �������
	public int addBook(Book book) {

		/* book.setContext(book.getContext().replace("\n", "</br>")); */

		return repository.insertBook(book);
	}

	// �����˻�
	public ArrayList<Book> searchBook(String search_name) {
		return repository.findByBookName(search_name);
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

		SimpleDateFormat fm = new SimpleDateFormat("yyyy�� MM�� dd�� hh:mm:ss");

		book.setRental_time(fm.format(cal.getTime()));

		cal.add(cal.MINUTE, 10);

		book.setReturn_schedule_time(fm.format(cal.getTime()));

		return book;
	}

	// �����ݳ�
	public String returnBook(int book_id) {
		System.out.println("-�����ݳ� ����-");

		String message = "�ݳ��Ǿ����ϴ�";

		Book book = repository.findByBookId(book_id);
		System.out.println("-�˻��� �ȵǴ°ǰ�-");

		// ��ü���� �Ǵ��� ��üī��Ʈ ����
		int Over_time_count = book.getOver_time_count();

		book = returnBookCheck(book);

		int cnt = repository.updateByReturnBook(book);
		
		if (Over_time_count < book.getOver_time_count()) {
			message = "�ݳ������� ���� �ݳ��Ǿ����ϴ�.";
		}

		return message;
	}

	// �ݳ��ð��� ��ü����
	public Book returnBookCheck(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy�� MM�� dd�� hh:mm:ss");

		try {
			// String�� Date�� ��ȯ
			if (fm.parse(book.getReturn_schedule_time()).after(cal.getTime())) { // ��ü����
				book.setOver_time_count(book.getOver_time_count() + 1);
				book.setReturn_time(fm.format(cal.getTime()));
				book.setRental_check(false);
				
				System.out.println(book.getOver_time_count());
				System.out.println(book.getReturn_time());
				System.out.println(book.getRental_check());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return book;

	}

	public Book detailBook(int book_id) {
		return repository.findByBookId(book_id);
	}

	public ArrayList<Book> rentalBookSearch(String search_name) {
		return repository.findByRentalBookName(search_name);
	}

}
