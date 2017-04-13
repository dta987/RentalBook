package com.web.bookrental.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.bookrental.dto.Book;
import com.web.bookrental.util.StatsFile;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	// �������
	public int addBook(Book book) {

		/* book.setContext(book.getContext().replace("\n", "</br>")); */

		return repository.insertBook(book);
	}

	// �����˻�
	public ArrayList<Book> searchBook(String search_name) {
		return repository.findByBookName(search_name);
	}

	// �����뿩
	public String rentalBook(int book_id) {

		String message;

		Book book = repository.findByBookId(book_id);

		if (book.getRental_check()) {
			message = "�뿩 ���� ���� �Դϴ�. \n�ݳ������ð�\n " + book.getReturn_schedule_time();
		} else {

			book = rentaltime(book);
			book.setRental_check(true);
			int cnt = repository.updatByRetalBook(book);
			message = "�뿩 �Ǿ����ϴ�. \n'" + book.getReturn_schedule_time() + "'\n���� �ݳ����ּ���";

		}

		return message;
	}

	// �뿩�ð��� �ݳ� �����ð�
	public Book rentaltime(Book book) {

		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy�� MM�� dd�� HH:mm:ss");

		book.setRental_time(fm.format(cal.getTime()));

		cal.add(cal.SECOND, 10);

		book.setReturn_schedule_time(fm.format(cal.getTime()));

		return book;
	}

	// �����ݳ�
	public String returnBook(int book_id) {
		// �⺻�޽���
		String message = "�ݳ��Ǿ����ϴ�";

		// �ݳ� �������� ��ȸ
		Book book = repository.findByBookId(book_id);

		// ��ü���� �Ǵ��� ��üī��Ʈ ����
		int Over_time_count = book.getOver_time_count();

		// �ݳ��ð��� ��ü����
		book = returnBookCheck(book);

		// �ݳ����� ������Ʈ
		int cnt = repository.updateByReturnBook(book);

		// ��ü���� �Ǵ� Over_time_count�� �����Ǿ��ٸ� ��ü�� �Ǵ�
		if (Over_time_count < book.getOver_time_count()) {
			message = "�ݳ������� ���� �ݳ��Ǿ����ϴ�.";
		}

		return message;
	}

	// �ݳ��ð��� ��ü����
	public Book returnBookCheck(Book book) {
		
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());

		SimpleDateFormat fm = new SimpleDateFormat("yyyy�� MM�� dd�� HH:mm:ss");

		try { // String�� Date�� ��ȯ �� ��ü���� �Ǵ�
				// ��ü�� //A.after(B) A�� ���� B�� �ð��� �� �����°�?
			if (cal.getTime().after(fm.parse(book.getReturn_schedule_time()))) {
				// ��üȽ�� ����
				book.setOver_time_count(book.getOver_time_count() + 1);
				book.setReturn_time(fm.format(cal.getTime()));
				book.setRental_check(false);
			} else {
				book.setReturn_time(fm.format(cal.getTime()));
				book.setRental_check(false);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return book;
	}

	// ���� �󼼺���
	public Book detailBook(int book_id) {
		return repository.findByBookId(book_id);
	}

	// �뿩���� ���� �˻�
	public ArrayList<Book> rentalBookSearch(String search_name) {
		return repository.findByRentalBookName(search_name);
	}

	// ��������
	public File statsBook(String file_name) {
		// �˻��̸� ���Ǽ���
		String search_name = "";
		// ���� ��ϰ�������
		ArrayList<Book> bookList = repository.findByBookName(search_name);

		// ������������
		StatsFile statsfile = new StatsFile();
		statsfile.setFile_Name(file_name);

		File file = createFile(statsfile, bookList);

		return file;
	}

	public File createFile(StatsFile statsFile, ArrayList<Book> bookList) {

		File file = new File(statsFile.getFile_Real_Path());
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

			bw.write("ID\t������\t���ǻ�\t����\t����\t�뿩Ƚ��\t��üȽ��\t�뿩����\t�뿩�ð�\t\t\t�ݳ��ð�");

			for (Book book : bookList) {
				String str = book.getBook_id() + "\t" + book.getBook_name() + "\t" + book.getCompany() + "\t"
						+ book.getWriter() + "\t" + book.getPrice() + "\t" + book.getRental_count() + "\t"
						+ book.getOver_time_count() + "\t";

				if (book.getRental_check()) {
					str += "�뿩��";
				} else {
					str += "�뿩����";
				}

				str += "\t" + book.getRental_time() + "\t\t\t" + book.getReturn_time();

				bw.write(str);
				bw.newLine();
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;

	}

}
