package com.synchrony.ImageUpload.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class IncorrectPasswordException extends RuntimeException {
	
	public IncorrectPasswordException(String message) {
		super(message);
	}
}
