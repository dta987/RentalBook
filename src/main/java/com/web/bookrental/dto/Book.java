package com.web.bookrental.dto;

public class Book {

	private int book_id;
	private String book_name;
	private String company;
	private String writer;
	private int price;
	private String context;
	private int rental_count = 0;
	private int over_time_count = 0;
	private String rental_time;
	private String return_time;
	private String return_schedule_time;
	private Boolean rental_check = false;

	@Override
	public String toString() {
		return "Book [도서번호=" + book_id + ", 도서명=" + book_name + ", 출판사=" + company + ", 저자=" + writer + ", 가격=" + price
				+ ", 대여=" + rental_check + "]";
	}

	public int getBook_id() {
		return book_id;
	}

	public String getContext() {
		return context;
	}

	public String getRental_time() {
		return rental_time;
	}

	public void setRental_time(String rental_time) {
		this.rental_time = rental_time;
	}

	public String getReturn_time() {
		return return_time;
	}

	public void setReturn_time(String return_time) {
		this.return_time = return_time;
	}

	public String getReturn_schedule_time() {
		return return_schedule_time;
	}

	public void setReturn_schedule_time(String return_schedule_time) {
		this.return_schedule_time = return_schedule_time;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRental_count() {
		return rental_count;
	}

	public void setRental_count(int rental_count) {
		this.rental_count = rental_count;
	}

	public int getOver_time_count() {
		return over_time_count;
	}

	public void setOver_time_count(int over_time_count) {
		this.over_time_count = over_time_count;
	}

	public Boolean getRental_check() {
		return rental_check;
	}

	public void setRental_check(Boolean rental_check) {
		this.rental_check = rental_check;
	}

}
