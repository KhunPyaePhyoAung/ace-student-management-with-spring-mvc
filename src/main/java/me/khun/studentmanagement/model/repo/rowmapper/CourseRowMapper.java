package me.khun.studentmanagement.model.repo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.support.jdbc.RowMapper;

public class CourseRowMapper implements RowMapper<Course> {

	@Override
	public Course mapRow(ResultSet rs, int index) throws SQLException {
		var course = new Course();
		course.setId(rs.getString("id"));
		course.setIdPrefix(rs.getString("id_prefix"));
		course.setIdCode(rs.getInt("id_code"));
		course.setName(rs.getString("name"));
		course.setShortName(rs.getString("short_name"));
		return course;
	}

}
