package com.web.bookrental.util;

import org.springframework.stereotype.Service;

@Service
public class Pageing {

	private int pageSize = 4; // �ѹ��� ������ �Խñ� ��
	private int beginPage = 0; // ���� ������
	private int endPage = 0; // �� ������
	private int beginRow = 0;
	// pageNumber*pageSize-(pageSize-1) // boginRow = (pageNumber-1) * pageSize +1
	private int endRow = 0; // ���� �������� �� ��
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
