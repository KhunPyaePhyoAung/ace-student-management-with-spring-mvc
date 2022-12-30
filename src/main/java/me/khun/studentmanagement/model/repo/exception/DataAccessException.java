package me.khun.studentmanagement.model.repo.exception;

import me.khun.studentmanagement.application.ApplicationException;

public class DataAccessException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		super();
	}
	
	public DataAccessException(String message) {
		super(message);
	}
	
	public DataAccessException(Throwable cause) {
		super(cause);
	}
	
	public DataAccessException(String message, Throwable cause) {
		super(cause);
	}
}
