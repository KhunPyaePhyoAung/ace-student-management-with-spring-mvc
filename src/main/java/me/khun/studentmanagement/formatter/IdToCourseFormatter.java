package me.khun.studentmanagement.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import me.khun.studentmanagement.model.dto.CourseDto;
import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.model.service.CourseService;

@Component
public class IdToCourseFormatter implements Formatter<Course> {
	
	@Autowired
	private CourseService courseService;

	@Override
	public String print(Course course, Locale locale) {
		return course == null ? null : course.getName();
	}

	@Override
	public Course parse(String id, Locale locale) throws ParseException {
		if (StringUtils.hasLength(id)) {
			return CourseDto.parse(courseService.findById(id));
		}
		return null;
	}

}
