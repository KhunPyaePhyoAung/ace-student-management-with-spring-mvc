package me.khun.studentmanagement.model.service.exception;

import java.util.HashMap;
import java.util.Map;

public class InvalidFieldsException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> exceptionMap;
	
	public InvalidFieldsException() {
		super();
		this.exceptionMap = new HashMap<>();
	}
	
	public void reject(String field, String message) {
		exceptionMap.put(field, message);
	}
	
	public String getErrorMessage(String field) {
		return exceptionMap.get(field);
	}

	public boolean isEmpty() {
		return exceptionMap.isEmpty();
	}
}
