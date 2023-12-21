package com.juank.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class ControllerExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(BooksBadRequestException.class)
	  public ResponseEntity<ErrorMessage> booksBadRequestException(BooksBadRequestException ex, WebRequest request) {
	    ErrorMessage message = new ErrorMessage(
	        HttpStatus.BAD_REQUEST.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false));
	    
	    logger.error("Bad Request Error", ex);
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	  }

	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		
	    ErrorMessage message = new ErrorMessage(
	        HttpStatus.INTERNAL_SERVER_ERROR.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false)
	    );
	    
	    logger.error("Bad Request Error", ex);
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
}
