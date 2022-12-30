package me.khun.studentmanagement.model.service;

import java.util.List;

import me.khun.studentmanagement.model.dto.CourseDto;
import me.khun.studentmanagement.model.entity.Course;

public interface CourseService {

    public CourseDto save(Course course);

    public boolean deleteById(String id);

    public CourseDto findById(String id);

    public List<CourseDto> findAll();

    public List<CourseDto> search(String keyword);

    public long getCount();

}