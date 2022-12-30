package me.khun.studentmanagement.model.service.exception;

public class InvalidFieldException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String errorCode;
	private String defaultMessage;
	
	public InvalidFieldException() {
		
	}
	
	public InvalidFieldException(String fieldName, String errorCode, String defaultMessage) {
		super(defaultMessage);
		this.fieldName = fieldName;
		this.errorCode = errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

}
