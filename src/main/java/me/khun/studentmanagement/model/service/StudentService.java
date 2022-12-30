package me.khun.studentmanagement.model.service;

import java.util.List;

import me.khun.studentmanagement.model.dto.StudentDto;
import me.khun.studentmanagement.model.entity.Student;

public interface StudentService {

    public StudentDto save(Student student);

    public boolean deleteById(String id);

    public StudentDto findById(String id);

    public List<StudentDto> findAll();

    public List<StudentDto> search(String studentKeyword, String courseKeyword);

    public long getCount();

}