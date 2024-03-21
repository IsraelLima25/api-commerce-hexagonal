package com.api.hexagonal.commerce.adapter.exception.handler.response;

public class ApiValidationErroResponse implements ApiSubErrorResponse {

	private final String object;
	private final String message;
	private Object rejectedValue;
	private String field;
	
	public ApiValidationErroResponse(String object, String message) {
		this.object = object;
		this.message = message;
	}

	public ApiValidationErroResponse(String object, String message, Object rejectedValue, String field) {
		this.object = object;
		this.message = message;
		this.rejectedValue = rejectedValue;
		this.field = field;
	}
	
	
	public String getObject() {
		return object;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getRejectedValue() {
		return rejectedValue;
	}
	
	public String getField() {
		return field;
	}
	
}
