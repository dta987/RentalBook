package com.web.bookrental.mapper;

import java.util.ArrayList;

import com.web.bookrental.dto.Book;
import com.web.bookrental.dto.BookSearch;

public interface BookMapper {

	public int insertBook(Book book);

	public ArrayList<Book> selectByBookName(BookSearch booksearch);

	public Book selectByBookId(int book_id);

	public int updatByRetalBook(Book book);

	public int updateByReturnBook(Book book);

	public ArrayList<Book> selectByRentalBookName(BookSearch booksearch);

	public int searchBookCountBySelect(String search_name);

	public ArrayList<Book> selectByBookAll();

	public int rentalBookCountBySelect(String search_name);

}
