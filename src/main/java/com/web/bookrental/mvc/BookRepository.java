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

	// �������
	public int insertBook(Book book) {
		return mapper.insertBook(book);
	}

	// �̸����� �˻�
	public ArrayList<Book> findByBookName(String search_name) {
		return mapper.selectByBookName(search_name);
	}

	// ���̵�� �˻�
	public Book findByBookId(int book_id) {
		return mapper.selectByBookId(book_id);

	}

	// ����
	public int updatByRetalBook(int book_id) {
		return mapper.updatByRetalBook(book_id);
	}

	public void updateByReturnBook(int book_id) {
		// TODO Auto-generated method stub
		
	}

}
