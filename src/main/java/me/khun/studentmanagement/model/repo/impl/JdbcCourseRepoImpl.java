package me.khun.studentmanagement.model.repo.impl;

import java.util.List;

import me.khun.studentmanagement.application.Application;
import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.model.repo.CourseRepo;
import me.khun.studentmanagement.model.repo.rowmapper.CourseRowMapper;
import me.khun.studentmanagement.support.jdbc.JdbcOperations;
import me.khun.studentmanagement.support.jdbc.KeyHolder;
import me.khun.studentmanagement.support.jdbc.RowMapper;

public class JdbcCourseRepoImpl implements CourseRepo {
	
	private String courseIdPrefix;
	private JdbcOperations jdbc;
	private RowMapper<Course> courseRowMapper;
	private String courseInsertSql;
	private String courseUpdateByIdSql;
	private String courseDeleteByIdSql;
	private String courseSelectByIdSql;
	private String courseSelectByStudentIdSql;
	private String courseSelectAllSql;
	private String courseSelectByIdAndNameLikeSql;
	private String courseSelectCountSql;
	
	public JdbcCourseRepoImpl() {
		jdbc = Application.getJdbcOperations();
		courseRowMapper = new CourseRowMapper();
		
		var sql = Application.getSqlPropertyReader();
		
		courseIdPrefix = Application.COURSE_ID_PREFIX;
		courseInsertSql = sql.getValue("course.insert");
		courseUpdateByIdSql = sql.getValue("course.updateById");
		courseDeleteByIdSql = sql.getValue("course.deleteById");
		courseSelectByIdSql = sql.getValue("course.selectById");
		courseSelectByStudentIdSql = sql.getValue("course.selectByStudentId");
		courseSelectAllSql = sql.getValue("course.selectAll");
		courseSelectByIdAndNameLikeSql = sql.getValue("course.selectByIdAndNameLike");
		courseSelectCountSql = sql.getValue("course.selectCount");
	}

	@Override
    public Course create(Course course) {
		var keyHolder = new KeyHolder();
        var params = List.of(
        			courseIdPrefix,
        			course.getName(),
        			course.getShortName()
        		);
        
        jdbc.insert(courseInsertSql, params, keyHolder);
        
        return jdbc.queryForObject(courseSelectByIdSql, List.of(courseIdPrefix + keyHolder.getKey().intValue()), courseRowMapper);
    }

	@Override
    public boolean update(Course course) {
		var params = List.of(
    			course.getName(),
    			course.getShortName(),
    			course.getId()
    		);
        return jdbc.update(courseUpdateByIdSql, params) > 0;
    }

	@Override
    public boolean deleteById(String id) {
        return jdbc.update(courseDeleteByIdSql, List.of(id)) > 0;
    }

	@Override
    public Course findById(String id) {
        return jdbc.queryForObject(courseSelectByIdSql, List.of(id), courseRowMapper);
    }
	
	@Override
	public List<Course> findByStudentId(String studentId) {
		return jdbc.queryForList(courseSelectByStudentIdSql, List.of(studentId), courseRowMapper);
	}

	@Override
    public List<Course> findAll() {
        return jdbc.queryForList(courseSelectAllSql, null, courseRowMapper);
    }

	@Override
    public List<Course> search(String keyword) {
        var params = List.of(
        			"%" + keyword + "%",
        			"%" + keyword + "%",
        			"%" + keyword + "%"
        		);
        return jdbc.queryForList(courseSelectByIdAndNameLikeSql, params, courseRowMapper);
    }

	@Override
    public long getCount() {
        return jdbc.queryForObject(courseSelectCountSql, null, Long.class);
    }

}