package com.api.hexagonal.commerce.adapter.exception.handler.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.ConstraintViolation;

@JsonTypeName("apierro")
public class ApiErroResponse {
	
	@JsonProperty("timestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private final LocalDateTime timestamp;
	
	@JsonProperty("status")
	private HttpStatus status;
	
	@JsonProperty("codeError")
	private Integer codeError;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("messageDetail")
	private String messageDetail;
	
	@JsonProperty("subErrors")
	private List<ApiSubErrorResponse> subErrors;
	
	private ApiErroResponse() {
		timestamp = LocalDateTime.now();
	}
	
	public ApiErroResponse(HttpStatus status) {
		this();
		this.status = status;
		this.codeError = status.value();
	}
	
	public ApiErroResponse(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.codeError = status.value();
		this.message = "Erro inesperado";
		this.messageDetail = ex.getLocalizedMessage();
	}
	
	public ApiErroResponse(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.codeError = status.value();
		this.message = message;
		this.messageDetail = ex.getLocalizedMessage();
	}
	
	private void addSubError(ApiSubErrorResponse subError) {
		if(subErrors == null) {
			subErrors = new ArrayList<>();
		}
		subErrors.add(subError);
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new ApiValidationErroResponse(object, field, rejectedValue, message));
	}
	
	private void addValidationError(String object, String message) {
		addSubError(new ApiValidationErroResponse(object, message));
	}
	
	public void addErrorBusiness(String code, String message) {
		addSubError(new ApiValidationErroResponse(code, message));
	}
	
	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}
	
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
	
	public void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(this::addValidationError);
	}
	
	private void addValidationError(ConstraintViolation<?> cv) {
		this.addValidationError(cv.getRootBeanClass().getSimpleName(), ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
	}
	
	public void addValidationError(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(this::addValidationError);
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
	public Integer getCodeError() {
		return codeError;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessageDetail() {
		return messageDetail;
	}
	
	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}
	
	public List<ApiSubErrorResponse> getSubErrors() {
		return subErrors;
	}
	
}
