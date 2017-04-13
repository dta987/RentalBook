package com.web.bookrental.mvc;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.bookrental.dto.Book;
import com.web.bookrental.util.Pageing;

@Controller
public class BookController {

	@Autowired
	private BookService service;

	// 도서등록
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addBook(Book book) {
		System.out.println("-도서등록-");
		int cnt = service.addBook(book);

		return "index";
	}

	// 도서검색
	@RequestMapping(value = "/searchbook", method = RequestMethod.GET)
	public String searchBook(Model model, String search_name) {
		System.out.println("-도서검색-");
		System.out.println("도서명 : " + search_name);
		
		ArrayList<Book> bookList = service.searchBook(search_name);

		// 검색결과목록
		model.addAttribute("bookList", bookList);

		return "index";
	}

	// 도서상세
	@RequestMapping(value = "/detailbook", method = RequestMethod.GET, produces = "application/JSON")
	public @ResponseBody Book detailBook(int book_id) {
		System.out.println("-도서 상세보기-");

		Book book = service.detailBook(book_id);

		System.out.println(book.toString());

		return book;
	}

	// 도서대여
	@RequestMapping(value = "/rentalbook", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
	public @ResponseBody String rentalBook(int book_id) {
		System.out.println("-도서대여-");

		String message = service.rentalBook(book_id);
		System.out.println(message);

		return message;
	}

	// 대여도서 검색
	@RequestMapping(value = "/rentalbooksearch", method = RequestMethod.GET)
	public String rentalBook(Model model, String search_name) {
		System.out.println("-대여도서 검색-");

		ArrayList<Book> rentalBookList = service.rentalBookSearch(search_name);

		model.addAttribute("rentalBookList", rentalBookList);

		return "returnBook";
	}

	// 도서 반납
	@RequestMapping(value = "/returnbook", method = RequestMethod.GET, produces = "application/text; charset=utf-8")
	public @ResponseBody String returnBook(int book_id) {
		System.out.println("-도서반납-");

		String message = service.returnBook(book_id);

		return message;
	}

	//도서파일 만들기
	@RequestMapping(value = "/statsbook", method = RequestMethod.GET)
	public ModelAndView statsBook(String file_name) {
		System.out.println("-도서통계-");
		
		File file = service.statsBook(file_name);	
		
		return new ModelAndView("springDownload", "downloadFile", file);

	}

}
