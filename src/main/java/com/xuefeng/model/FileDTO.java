package com.xuefeng.model;

public class FileDTO {

	private byte[] data;

	
	
	public FileDTO(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
