package com.web.bookrental.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.web.bookrental.dto.Book;

public class CreateFile {
		
	public File createTextFile(StatsFile statsfile, ArrayList<Book> bookList) {

		File file = new File(statsfile.getFile_real_path() + ".txt");

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

			bw.write("ID\t������\t���ǻ�\t����\t����\t�뿩Ƚ��\t��üȽ��\t�뿩����\t�뿩�ð�\t\t\t�ݳ��ð�");
			bw.newLine();

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
	

	// �������� ����
	public File createExcelFile(StatsFile statsFile, ArrayList<Book> bookList, String[] head) {

		File file = new File(statsFile.getFile_real_path() + ".xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;

		// �������
		for (int i = 0; i < head.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(head[i]);
		}

		// row����
		int rowindex = 1;
		for (Book book : bookList) {
			row = sheet.createRow(rowindex);

			cell = row.createCell(0); // ID
			cell.setCellValue(book.getBook_id());
			cell = row.createCell(1); // ������
			cell.setCellValue(book.getBook_name());
			cell = row.createCell(2); // ���ǻ�
			cell.setCellValue(book.getCompany());
			cell = row.createCell(3); // ����
			cell.setCellValue(book.getWriter());
			cell = row.createCell(4); // ����
			cell.setCellValue(book.getPrice());
			cell = row.createCell(5); // �뿩Ƚ��
			cell.setCellValue(book.getRental_count());
			cell = row.createCell(6); // ��üȽ��
			cell.setCellValue(book.getOver_time_count());
			cell = row.createCell(7); // �뿩����
			cell.setCellValue(book.getRental_check());
			cell = row.createCell(8); // �뿩�ð�
			cell.setCellValue(book.getRental_time());
			cell = row.createCell(9); // �ݳ��ð�
			cell.setCellValue(book.getReturn_time());
			cell = row.createCell(10); // �ݳ������ð�
			cell.setCellValue(book.getReturn_schedule_time());

			rowindex++;

		}

		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(file);
			workbook.write(fos);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (workbook != null) {
					workbook.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return file;
	}

}
