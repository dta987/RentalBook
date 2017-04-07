package com.web.bookrental.mvc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.bookrental.dto.Book;
import com.web.bookrental.mapper.BookMapper;

@Repository
public class BookRepository {

	@Autowired
	private BookMapper mapper;

	// 도서등록
	public int insertBook(Book book) {
		return mapper.insertBook(book);
	}

	// 이름으로 검색
	public ArrayList<Book> findByBookName(String search_name) {
		return mapper.selectByBookName(search_name);
	}

	// 아이디로 검색
	public Book findByBookId(int book_id) {
		return mapper.selectByBookId(book_id);

	}

	// 도서대여
	public int updatByRetalBook(Book book) {
		return mapper.updatByRetalBook(book);
	}

	// 도서반납
	public int updateByReturnBook(Book book) {
		return mapper.updateByReturnBook(book);

	}

}
