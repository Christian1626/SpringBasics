package com.basics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="This user is not found")
public class UserNotFoundException extends Exception 
{
	private static final long serialVersionUID = 100L;
}