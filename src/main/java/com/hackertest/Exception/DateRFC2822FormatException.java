package com.hackertest.Exception;

public class DateRFC2822FormatException extends Exception {

	private static final long serialVersionUID = -2584498577231851579L;
	
	
	String message;
	
	public DateRFC2822FormatException(Exception e) {
		super(e);
		this.message = "Generic Exception";
	}
	
	public DateRFC2822FormatException(String message, Exception e) {
		super(e);
		this.message = message;
	}
}
