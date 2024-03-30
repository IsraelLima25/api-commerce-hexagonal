package com.api.hexagonal.commerce.adapter.exception;

public class RegystryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public RegystryNotFoundException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
