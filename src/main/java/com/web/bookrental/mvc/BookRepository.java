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

	// �����뿩
	public int updatByRetalBook(Book book) {
		return mapper.updatByRetalBook(book);
	}

	// �����ݳ�
	public int updateByReturnBook(Book book) {
		return mapper.updateByReturnBook(book);

	}

}
