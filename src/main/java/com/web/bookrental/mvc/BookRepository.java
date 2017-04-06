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

	// 수정
	public int updatByRetalBook(int book_id) {
		return mapper.updatByRetalBook(book_id);
	}

	public void updateByReturnBook(int book_id) {
		// TODO Auto-generated method stub
		
	}

}
