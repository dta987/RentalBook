package com.web.bookrental.util;

import org.springframework.stereotype.Service;

@Service
public class Pageing {

	private int pageSize = 4; // 한번에 보여줄 게시글 수
	private int beginPage = 0; // 시작 페이지
	private int endPage = 0; // 끝 페이지
	private int beginRow = 0;
	// pageNumber*pageSize-(pageSize-1) // boginRow = (pageNumber-1) * pageSize +1
	private int endRow = 0; // 현재 페이지의 끝 행
	// pageNumber*pageCount // endRow = pageNumber * pageSize

	public int getBeginRow(int selectPage) {

		this.beginRow = selectPage * this.pageSize - (this.pageSize - 1);
		
		return beginRow;
	}

	public int getendRow(int selectpage) {
		
		this.endRow = selectpage * this.pageSize;
		
		return endRow;
	}

	public int getBeginPage(int selectPage) {

		this.beginPage = (selectPage - 1) / this.pageSize * this.pageSize + 1;

		return beginPage;
	}

	public int getEndPage(int totalpage) {

		this.endPage = this.beginPage + this.pageSize - 1;

		if (endPage > totalpage) {

			this.endPage = totalpage;
		}

		return endPage;
	}

	public int getPageSize() {
		return pageSize;
	}

}
