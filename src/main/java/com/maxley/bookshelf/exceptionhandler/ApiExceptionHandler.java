package com.maxley.bookshelf.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maxley.bookshelf.exception.DomainException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired 
	private MessageSource messageSource;
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request){
		
		ExceptionProperty exceptionProperty = new ExceptionProperty();
		
		var status = HttpStatus.NOT_FOUND;
		exceptionProperty.setStatus(status.value());
		exceptionProperty.setTitle(ex.getMessage());
		exceptionProperty.setDataTime(LocalDateTime.now());
		
		return handleExceptionInternal(ex, exceptionProperty, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ExceptionProperty exception = new ExceptionProperty();
		List<ExceptionPropertyField> fields = new ArrayList<ExceptionPropertyField>();
		
		for(ObjectError error: ex.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			ExceptionPropertyField field = new ExceptionPropertyField(fieldName, message);
			fields.add(field);
		}
		
		exception.setStatus(status.value());
		exception.setDataTime(LocalDateTime.now());
		exception.setTitle("Invalid Fields");
		exception.setFields(fields);
		
		return handleExceptionInternal(ex, exception, headers, status, request);
	}
}
