package me.khun.studentmanagement.model.service.validator;

import me.khun.studentmanagement.model.dto.CourseDto;
import me.khun.studentmanagement.model.service.exception.InvalidFieldsException;

public class CourseValidator implements Validator<CourseDto> {
	
	public static final int MAX_COURSE_CODE_LENGTH = 20;
	public static final int MAX_COURSE_NAME_LENGTH = 100;
	public static final int MAX_COURSE_SHORT_NAME_LENGTH = 10;

	@Override
	public InvalidFieldsException validate(CourseDto course) {
		
		var exp = new InvalidFieldsException();
		
		course.setName(course.getName() == null ? null : course.getName().trim());
		course.setShortName(course.getShortName() == null ? null : course.getShortName().trim());
		
		
		if (course.getName() == null || course.getName().isBlank()) {
			exp.reject("name", "Enter course name");
		}
		
		if (course.getName() != null && !course.getName().isBlank() && course.getName().length() > MAX_COURSE_NAME_LENGTH) {
			exp.reject("name", "Course name cannot be exceed %d characters".formatted(MAX_COURSE_NAME_LENGTH));
		}
		
		if (course.getShortName() == null || course.getShortName().isBlank()) {
			exp.reject("shortName", "Enter short name");
		}
		
		if (course.getShortName() != null && !course.getShortName().isBlank() && course.getShortName().length() > MAX_COURSE_SHORT_NAME_LENGTH) {
			exp.reject("shortName", "Course short cannot be exceed %d characters".formatted(MAX_COURSE_SHORT_NAME_LENGTH));
		}
		
		return exp;
	}

}
