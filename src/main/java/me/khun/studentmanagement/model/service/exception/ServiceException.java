package me.khun.studentmanagement.model.service.exception;

import me.khun.studentmanagement.application.ApplicationException;

public class ServiceException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(cause);
	}
}
