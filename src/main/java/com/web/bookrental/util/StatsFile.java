package com.web.bookrental.util;

public class StatsFile {

	private String file_name; // 파일명
	private String file_path = "C:\\Users\\admin\\git\\rentalBook\\src\\main\\webapp\\statsfile\\"; // 파일경로
	private String file_real_path;

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public String getFile_real_path() {
		this.file_real_path = this.file_path + this.file_name;
		return this.file_real_path;
	}
}
