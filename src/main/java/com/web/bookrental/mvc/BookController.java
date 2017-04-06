package com.web.bookrental.mvc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.bookrental.dto.Book;

@Controller
public class BookController {

	@Autowired
	private BookService service;

	// 도서등록 페이지 이동
	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public String addBookPage() {
		System.out.println("-도서등록페이지 이동-");
		System.out.println("----------------");
		return "AddBook";

	}

	// 도서등록
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addBook(Book book) {
		System.out.println("-도서등록-");
		int cnt = service.addBook(book);

		return "index";

	}

	// 도서검색
	@RequestMapping(value = "/searchbook", method = RequestMethod.GET)
	public String searchBook(Model model, @RequestParam String search_name) {
		System.out.println("-도서검색-");
		System.out.println("도서명 : " + search_name);

		ArrayList<Book> bookList = service.searchBook(search_name);

		model.addAttribute("bookList", bookList);// 검색결과목록

		return "index";

	}

	// 도서대여
	@RequestMapping(value = "/rentalbook", method = RequestMethod.GET)
	public String rentalBook(int book_id) {
		System.out.println("-도서대여-");

		int cnt = service.rentalBook(book_id);

		return "index";
	}

	@RequestMapping(value = "/returnbook", method = RequestMethod.GET)
	public String returnBook(int book_id) {
		System.out.println("-도서반납-");

		int cnt = service.returnBook(book_id);

		return null;

	}

}
