package com.web.bookrental.mvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
		return repository.insertBook(book);
	}

	// 도서검색
	public ArrayList<Book> searchBook(String search_name) {
		return repository.findByBookName(search_name);
	}

	// 도서대여
	public int rentalBook(int book_id) {
		return repository.updatByRetalBook(book_id);
	}

	// 도서반납
	public int returnBook(int book_id) {
		
		repository.updateByReturnBook(book_id);
		
		return 0;
	}

}
