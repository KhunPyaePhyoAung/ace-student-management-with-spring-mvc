package me.khun.studentmanagement.model.repo;

import java.util.List;

import me.khun.studentmanagement.model.entity.Course;

public interface CourseRepo extends BaseRepo<Course, String> {

	public List<Course> findByStudentId(String studentId);
	
    public List<Course> search(String k);


}