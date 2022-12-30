package me.khun.studentmanagement.model.repo;

import java.util.List;

import me.khun.studentmanagement.model.entity.Student;

public interface StudentRepo extends BaseRepo<Student, String> {

    public List<Student> search(String studentKeyword, String courseKeyword);

}