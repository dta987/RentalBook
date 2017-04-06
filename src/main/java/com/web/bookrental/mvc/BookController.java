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

	// ������� ������ �̵�
	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public String addBookPage() {
		System.out.println("-������������� �̵�-");
		System.out.println("----------------");
		return "AddBook";

	}

	// �������
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addBook(Book book) {
		System.out.println("-�������-");
		int cnt = service.addBook(book);

		return "index";

	}

	// �����˻�
	@RequestMapping(value = "/searchbook", method = RequestMethod.GET)
	public String searchBook(Model model, @RequestParam String search_name) {
		System.out.println("-�����˻�-");
		System.out.println("������ : " + search_name);

		ArrayList<Book> bookList = service.searchBook(search_name);

		model.addAttribute("bookList", bookList);// �˻�������

		return "index";

	}

	// �����뿩
	@RequestMapping(value = "/rentalbook", method = RequestMethod.GET)
	public String rentalBook(int book_id) {
		System.out.println("-�����뿩-");

		int cnt = service.rentalBook(book_id);

		return "index";
	}

	@RequestMapping(value = "/returnbook", method = RequestMethod.GET)
	public String returnBook(int book_id) {
		System.out.println("-�����ݳ�-");

		int cnt = service.returnBook(book_id);

		return null;

	}

}
