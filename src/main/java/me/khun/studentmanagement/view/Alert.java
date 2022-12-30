package me.khun.studentmanagement.view;

public class Alert {

	public enum Type {
		SUCCESS, WARNING, ERROR, INFO
	}

	private String message;
	private Type type;

	public Alert() {}
	
	public Alert(String message, Type type) {
		this.message = message;
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
