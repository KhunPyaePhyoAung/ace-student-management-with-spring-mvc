package me.khun.studentmanagement.model.service.validator;

import me.khun.studentmanagement.model.dto.UserDto;
import me.khun.studentmanagement.model.service.exception.InvalidFieldsException;

public class UserValidator implements Validator<UserDto> {
	
	public static final int MAX_NAME_LENGTH = 50;
	public static final int MAX_EMAIL_LENGTH = 255;
	public static final int MIN_PASSWORD_LENGTH = 4;
	public static final int MAX_PASSWORD_LENGTH = 50;

	@Override
	public InvalidFieldsException validate(UserDto user) {
		user.setName(user.getName() == null ? null : user.getName().trim());
		user.setEmail(user.getEmail() == null ? null : user.getEmail().trim());
		user.setPassword(user.getPassword() == null ? null : user.getPassword().trim());
		user.setConfirmPassword(user.getConfirmPassword() == null ? null : user.getConfirmPassword());
		var exp = new InvalidFieldsException();
		
		if (user.getName() == null || user.getName().isBlank()) {
			exp.reject("name", "Enter name");
		}
		
		if (user.getName() != null && !user.getName().isBlank() && user.getName().length() > MAX_NAME_LENGTH) {
			exp.reject("name", "Name cannot be exceed %d characters".formatted(MAX_NAME_LENGTH));
		}
		
		if (user.getRole() == null) {
			exp.reject("role", "Select a role");
		}
		
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			exp.reject("email", "Enter email");
		}
		
		if (user.getEmail() != null && user.getEmail().isBlank() && user.getEmail().length() > MAX_EMAIL_LENGTH) {
			exp.reject("email", "Email cannot be exceed %d characters".formatted(MAX_EMAIL_LENGTH));
		}
		
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			exp.reject("password", "Enter password");
		}
		
		if (user.getPassword() != null && !user.getPassword().isBlank() && user.getPassword().length() < MIN_PASSWORD_LENGTH) {
			exp.reject("password", "Password must be at least %d characters".formatted(MIN_PASSWORD_LENGTH));
		}
		
		if (user.getPassword() != null && !user.getPassword().isBlank() && user.getPassword().length() > MAX_PASSWORD_LENGTH) {
			exp.reject("password", "Password cannot be exceed %d characters".formatted(MAX_PASSWORD_LENGTH));
		}
		
		if (user.getConfirmPassword() == null || user.getConfirmPassword().isBlank()) {
			exp.reject("confirmPassword", "Confirm password");
		}
		
		if (user.getConfirmPassword() != null && !user.getConfirmPassword().isBlank() && !user.getConfirmPassword().equals(user.getPassword())) {
			exp.reject("confirmPassword", "Passwords are not the same");
		}
		
		return exp;
	}

}
