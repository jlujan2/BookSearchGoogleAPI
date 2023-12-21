package com.juank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BooksBadRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BooksBadRequestException(String msg) {
		super(msg);
	}

}
