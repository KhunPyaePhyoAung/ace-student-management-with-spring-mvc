package me.khun.studentmanagement.model.repo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.khun.studentmanagement.model.entity.Student;
import me.khun.studentmanagement.model.entity.Student.Gender;
import me.khun.studentmanagement.support.jdbc.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int index) throws SQLException {
		var student = new Student();
		student.setId(rs.getString("id"));
		student.setIdPrefix(rs.getString("id_prefix"));
		student.setIdCode(rs.getInt("id_code"));
		student.setName(rs.getString("name"));
		student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
		student.setGender(Gender.valueOf(rs.getString("gender")));
		student.setPhone(rs.getString("phone"));
		student.setEducation(rs.getString("education"));
		
		return student;
	}

}
