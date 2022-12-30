package me.khun.studentmanagement.model.service.impl;

import java.util.List;

import me.khun.studentmanagement.model.dto.StudentDto;
import me.khun.studentmanagement.model.entity.Student;
import me.khun.studentmanagement.model.repo.StudentRepo;
import me.khun.studentmanagement.model.repo.exception.DataAccessException;
import me.khun.studentmanagement.model.service.StudentService;
import me.khun.studentmanagement.model.service.exception.ServiceException;

public class StudentServiceImpl implements StudentService {
	
	private StudentRepo studentRepo;
	
	public StudentServiceImpl(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}

	@Override
    public StudentDto save(Student student) {
		
		try {
			if (student.getId() == null || student.getId().isBlank()) {
				var created = studentRepo.create(student);
	        	return StudentDto.of(created);
	        }
			studentRepo.update(student);
			return findById(student.getId());
		} catch (DataAccessException e) {
			throw new ServiceException("Could not create the student");
		}
    }

    @Override
    public boolean deleteById(String id) {
    	if (id == null || id.isBlank()) {
			throw new ServiceException("Invalid student id");
		}
		
        try {
        	return studentRepo.deleteById(id);
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not delete this student.");
        }
    }

    @Override
    public StudentDto findById(String id) {
    	if (id == null || id.isBlank()) {
			return null;
		}
        
		try {
			var entity = studentRepo.findById(id);
			if (entity == null) {
				return null;
			}
			return StudentDto.of(entity);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the student.");
		}
    }

    @Override
    public List<StudentDto> findAll() {
    	try {
			return StudentDto.ofList(studentRepo.findAll());
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the students.");
		}
    }

    @Override
    public List<StudentDto> search(String studentKeywrod, String courseKeyword) {
        studentKeywrod = studentKeywrod == null ? "" : studentKeywrod;
        courseKeyword = courseKeyword == null ? "" : courseKeyword;
        
        try {
        	return StudentDto.ofList(studentRepo.search(studentKeywrod, courseKeyword));
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not search the students.");
        }
    }

    @Override
    public long getCount() {
    	try {
        	return studentRepo.getCount();
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not get the student count.");
        }
    }

}