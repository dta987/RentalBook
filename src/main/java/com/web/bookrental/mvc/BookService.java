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

	// 도서등록
	public int addBook(Book book) {
		
		/*book.setContext(book.getContext().replace("\n", "</br>"));*/
		
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
			message = "대여 중인 도서 입니다.";
		} else {

			book = rentaltime(book);
			book.setRental_check(true);
			int cnt = repository.updatByRetalBook(book);
			message = "대여 되었습니다. '" + book.getReturn_schedule_time() + "'까지 반납해주세요";

		}

		return message;
	}

	// 대여시간과 반납 예정시간
	public Book rentaltime(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

		book.setRental_time(fm.format(cal.getTime()));

		cal.add(cal.MINUTE, 10);

		book.setReturn_schedule_time(fm.format(cal.getTime()));

		return book;
	}

	// 도서반납
	public String returnBook(int book_id) {
		
		String message = "반납되었습니다";
		
		Book book = repository.findByBookId(book_id);
		
		//연체여부 판단전 연체카운트 저장
		int Over_time_count = book.getOver_time_count();
		
		book = returnBookCheck(book);
		
		repository.updateByReturnBook(book);
		
		if(Over_time_count < book.getOver_time_count()) {
			message = "반납기한이 지나 반납되었습니다.";
		}
		
		return message;
	}
	
	//반납시간과 연체여부
	public Book returnBookCheck(Book book) {
			
		
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());
		
		SimpleDateFormat fm = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");

		try {
			//String을 Date로 변환
			Date getReturn_schedule_time = new SimpleDateFormat().parse(book.getReturn_schedule_time());
			
			if(getReturn_schedule_time.after(cal.getTime())) { //연체 여부
								
				book.setOver_time_count(book.getOver_time_count()+1);
				book.setReturn_time(fm.format(cal.getTime()));
				book.setRental_check(false);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return book;

	}

	public Book detailBook(int book_id) {
		return repository.findByBookId(book_id);
	}

}
