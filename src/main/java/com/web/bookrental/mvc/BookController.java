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
import com.web.bookrental.dto.BookSearch;

@Controller
public class BookController {

	@Autowired
	private BookService service;

	// 도서등록
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addBook(Book book) {
		System.out.println("-도서등록-");
		int cnt = service.addBook(book);

		return "redirect:/book/searchbook";
	}

	// 도서검색
	@RequestMapping(value = "/searchbook", method = RequestMethod.GET)
	public String searchBook(Model model, BookSearch booksearch) {
		System.out.println("-도서검색-");
		System.out.println("도서명 : " + booksearch.getSearch_name());
		
		ArrayList<Book> bookList = service.searchBook(booksearch);
				
		model.addAttribute("bookList", bookList);
		model.addAttribute("beginPage", booksearch.getBeginPage());
		model.addAttribute("endPage", booksearch.getEndPage());
		model.addAttribute("selectPage", booksearch.getSelectPage());
		model.addAttribute("search_name", booksearch.getSearch_name());
		
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
	public String rentalBook(Model model, BookSearch booksearch) {
		System.out.println("-대여도서 검색-");

		ArrayList<Book> rentalBookList = service.rentalBookSearch(booksearch);

		model.addAttribute("rentalBookList", rentalBookList);
		model.addAttribute("beginPage", booksearch.getBeginPage());
		model.addAttribute("endPage", booksearch.getEndPage());
		model.addAttribute("selectPage", booksearch.getSelectPage());
		model.addAttribute("search_name", booksearch.getSearch_name());

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
	public ModelAndView statsBook(String file_name, String extension) {
		System.out.println("-도서통계-");
		System.out.println(extension);
		
		File file = service.statsBook(file_name, extension);	
		
		return new ModelAndView("Download", "downloadFile", file);
	}

}
