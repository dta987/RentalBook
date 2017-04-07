package com.web.bookrental.mvc;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//������
	@RequestMapping(value = "/detailbook", method = RequestMethod.GET, produces = "application/JSON")
	public @ResponseBody Book detailBook(@RequestParam int book_id) {
		System.out.println("-���� �󼼺���-");
		
		Book book = service.detailBook(book_id);
		
		System.out.println(book.toString());
		
			
		return book;
		
	}

	// �����뿩
	@RequestMapping(value = "/rentalbook", method = RequestMethod.GET)
	public String rentalBook(Model model, int book_id) {
		System.out.println("-�����뿩-");

		String message = service.rentalBook(book_id);
		model.addAttribute("message", message);

		return "index";
	}

	// ���� �ݳ�
	@RequestMapping(value = "/returnbook", method = RequestMethod.GET)
	public String returnBook(int book_id) {
		System.out.println("-�����ݳ�-");

		String message = service.returnBook(book_id);

		return null;

	}

}
