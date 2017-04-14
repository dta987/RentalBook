package com.web.bookrental.dto;

public class BookSearch {

	private int selectPage = 1;
	private String search_name = "";
	private int beginLow = 0;
	private int endRow = 0; // 현재 페이지의 끝 행
	private int beginPage;
	private int endPage;

	@Override
	public String toString() {
		return "BookSearch [selectPage=" + selectPage + ", search_name=" + search_name + ", beginLow=" + beginLow
				+ ", endRow=" + endRow + ", beginPage=" + beginPage + ", endPage=" + endPage + "]";
	}

	public int getSelectPage() {
		return selectPage;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setSelectPage(int selectPage) {
		this.selectPage = selectPage;
	}

	public String getSearch_name() {
		return search_name;
	}

	public void setSearch_name(String search_name) {
		this.search_name = search_name;
	}

	public int getBeginLow() {
		return beginLow;
	}

	public void setBeginLow(int beginLow) {
		this.beginLow = beginLow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

}
