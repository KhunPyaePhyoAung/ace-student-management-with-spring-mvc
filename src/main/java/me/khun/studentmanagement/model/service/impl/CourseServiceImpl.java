package me.khun.studentmanagement.model.service.impl;

import java.util.List;

import me.khun.studentmanagement.model.dto.CourseDto;
import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.model.repo.CourseRepo;
import me.khun.studentmanagement.model.repo.exception.DataAccessException;
import me.khun.studentmanagement.model.service.CourseService;
import me.khun.studentmanagement.model.service.exception.InvalidFieldException;
import me.khun.studentmanagement.model.service.exception.ServiceException;

public class CourseServiceImpl implements CourseService {
	
	private CourseRepo courseRepo;
	
	public CourseServiceImpl(CourseRepo courseRepo) {
		this.courseRepo = courseRepo;
	}

	@Override
    public CourseDto save(Course course) {
		
		try {
			if (course.getId() == null || course.getId().isBlank()) {
				var created = courseRepo.create(course);
	        	return CourseDto.of(created);
	        }
			courseRepo.update(course);
			return CourseDto.of(course);
		} catch (DataAccessException e) {
			throw new InvalidFieldException("name", "duplicated", "A course with this name already exists");
		}
    }

	@Override
    public boolean deleteById(String id) {
		if (id == null || id.isBlank()) {
			throw new ServiceException("Invalid course id");
		}
		
        try {
        	return courseRepo.deleteById(id);
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not delete this course.");
        }
    }

	@Override
    public CourseDto findById(String id) {
		if (id == null || id.isBlank()) {
			return null;
		}
        
		try {
			var entity = courseRepo.findById(id);
			if (entity == null) {
				return null;
			}
			return CourseDto.of(entity);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the course.");
		}
    }

	@Override
    public List<CourseDto> findAll() {
		try {
			return CourseDto.ofList(courseRepo.findAll());
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the courses.");
		}
    }

	@Override
    public List<CourseDto> search(String keyword) {
		keyword = keyword == null ? "" : keyword;
        try {
        	return CourseDto.ofList(courseRepo.search(keyword));
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not search the courses.");
        }
    }

	@Override
    public long getCount() {
		try {
        	return courseRepo.getCount();
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not get the course count.");
        }
    }

}