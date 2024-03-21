package com.api.hexagonal.commerce.adapter.exception.handler;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.hexagonal.commerce.adapter.exception.RegystryNotFoundException;
import com.api.hexagonal.commerce.adapter.exception.handler.response.ApiErroResponse;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class ResourceExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiErroResponse handlerArgumentNotValid(MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        var apiErrorResponse = new ApiErroResponse(HttpStatus.BAD_REQUEST);
        apiErrorResponse.addValidationErrors(fieldErrors);
        
        return apiErrorResponse;
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({RegystryNotFoundException.class})
    public ResponseEntity<Object> handlerRegystryNotFound(RegystryNotFoundException ex) {
		
		var apiErrorResponse = new ApiErroResponse(HttpStatus.NOT_FOUND, ex);
		return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

}
