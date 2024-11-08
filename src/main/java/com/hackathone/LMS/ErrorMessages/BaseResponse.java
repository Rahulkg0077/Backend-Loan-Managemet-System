package com.hackathone.LMS.ErrorMessages;

import java.util.List;

public class BaseResponse<T> {

	private List<T> data;
	private String message;
	private int code;
	private int status;
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getrCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int staus) {
		this.status = staus;
	}
	public BaseResponse(List<T> data, String message, int code, int status) {
		super();
		this.data = data;
		this.message = message;
		this.code = code;
		this.status = status;
	}
	
	public BaseResponse() {
	}

	
	
}
