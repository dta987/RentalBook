package com.web.bookrental.mapper;

import java.util.ArrayList;

import com.web.bookrental.dto.Book;

public interface BookMapper {

	public int insertBook(Book book);

	public ArrayList<Book> selectByBookName(String search_name);

	public Book selectByBookId(int book_id);

	public int updatByRetalBook(Book book);

	public int updateByReturnBook(Book book);

}
