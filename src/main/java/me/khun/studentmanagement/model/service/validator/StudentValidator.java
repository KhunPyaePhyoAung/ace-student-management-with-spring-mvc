package me.khun.studentmanagement.model.service.validator;

import me.khun.studentmanagement.model.dto.StudentDto;
import me.khun.studentmanagement.model.service.exception.InvalidFieldsException;

public class StudentValidator implements Validator<StudentDto> {
	
	public static final int MAX_NAME_LENGTH = 100;
	public static final int MIN_PHONE_LENGTH = 6;
	public static final int MAX_PHONE_LENGTH = 15;

	@Override
	public InvalidFieldsException validate(StudentDto student) {
		
		var exp = new InvalidFieldsException();
		
		student.setName(student.getName() == null ? null : student.getName().trim());
		student.setPhone(student.getPhone() == null ? null : student.getPhone().trim());
		student.setEducation(student.getEducation() == null ? null : student.getEducation().trim());
		
		if (student.getName() == null || student.getName().isBlank()) {
			exp.reject("name", "Enter name");
		}
		
		if (student.getName() != null && !student.getName().isBlank() && student.getName().length() > MAX_NAME_LENGTH) {
			exp.reject("name", "Name cannot be exceed %d characters".formatted(MAX_NAME_LENGTH));
		}
		
		if (student.getDateOfBirth() == null) {
			exp.reject("dateOfBirth", "Select date of birth");
		}
		
		if (student.getGender() == null) {
			exp.reject("gender", "Select a gender");
		}
		
		if (student.getPhone() == null || student.getPhone().isBlank()) {
			exp.reject("phone", "Enter phone number");
		}
		
		if (student.getPhone() != null && !student.getPhone().isBlank() && student.getPhone().length() < MIN_PHONE_LENGTH) {
			exp.reject("phone", "Phone number must be at least %d characters".formatted(MIN_PHONE_LENGTH));
		}
		
		if (student.getPhone() != null && !student.getPhone().isBlank() && student.getPhone().length() > MAX_PHONE_LENGTH) {
			exp.reject("phone", "Phone number cannot be exceed %d characters".formatted(MAX_PHONE_LENGTH));
		}
		
		if (student.getEducation() == null || student.getEducation().isBlank()) {
			exp.reject("education", "Select education");
		}
		
		
		if (student.getCourses() == null || student.getCourses().isEmpty()) {
			exp.reject("courses", "Select a course");
		}
		
		return exp;
	}

}
