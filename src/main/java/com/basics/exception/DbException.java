package com.basics.exception;

public class DbException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbException() {
		super();
	}
	
	public DbException(String message) {
		super(message);
	}
	
	public DbException(String message, Throwable cause) {
		super(message, cause);
	}
}
