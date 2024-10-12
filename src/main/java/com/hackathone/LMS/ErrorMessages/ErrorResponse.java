package com.hackathone.LMS.ErrorMessages;

public class ErrorResponse {

	
	private String message;
	private int errorCode;
	
	public ErrorResponse(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return errorCode;
	}

	public void setCode(int code) {
		this.errorCode = code;
	}
	
	
}
