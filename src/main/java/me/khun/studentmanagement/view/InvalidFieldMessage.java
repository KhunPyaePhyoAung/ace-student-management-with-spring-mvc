package me.khun.studentmanagement.view;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import me.khun.studentmanagement.model.service.exception.InvalidFieldsException;

public class InvalidFieldMessage extends SimpleTagSupport {
	
	private String cssClass;
	
	private String field;
	
	private InvalidFieldsException exception;
	
	private static final String TAG = """
			<div class="%s">
				%s
			</div>
			""";

	@Override
	public void doTag() throws JspException, IOException {
		if (exception == null || exception.isEmpty()) {
			return;
		}
		var writer = getJspContext().getOut();
		var errorMessage = exception.getErrorMessage(field);
		if (errorMessage != null) {
			writer.append(TAG.formatted(cssClass, errorMessage));
		}
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public InvalidFieldsException getException() {
		return exception;
	}

	public void setException(InvalidFieldsException exception) {
		this.exception = exception;
	}
}
