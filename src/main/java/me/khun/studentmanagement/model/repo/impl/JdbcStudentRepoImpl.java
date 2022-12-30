package me.khun.studentmanagement.model.repo.impl;

import java.util.ArrayList;
import java.util.List;

import me.khun.studentmanagement.application.Application;
import me.khun.studentmanagement.model.entity.Student;
import me.khun.studentmanagement.model.repo.CourseRepo;
import me.khun.studentmanagement.model.repo.StudentRepo;
import me.khun.studentmanagement.model.repo.rowmapper.StudentRowMapper;
import me.khun.studentmanagement.support.jdbc.JdbcOperations;
import me.khun.studentmanagement.support.jdbc.KeyHolder;
import me.khun.studentmanagement.support.jdbc.RowMapper;

public class JdbcStudentRepoImpl implements StudentRepo {
	
	private JdbcOperations jdbc;
	private RowMapper<Student> studentRowMapper;
	private CourseRepo courseRepo;
	private String studentIdPrefix;
	private String studentInsertSql;
	private String studentSelectAllSql;
	private String studentSelectByIdSql;
	private String studentSelectByStudentAndCourseSql;
	private String studentSelectCountSql;
	private String studentUpdateByIdSql;
	private String studentDeleteByIdSql;
	private String studentCourseInsertSql;
	private String studentCoursedeleteByCourseIdNotINAndStudentIdSql;
	
	public JdbcStudentRepoImpl() {
		jdbc = Application.getJdbcOperations();
		studentRowMapper = new StudentRowMapper();
		courseRepo = Application.getCourseRepo();
		studentIdPrefix = Application.STUDENT_ID_PREFIX;
		
		var sql = Application.getSqlPropertyReader();
		
		studentInsertSql = sql.getValue("student.insert");
		studentSelectAllSql = sql.getValue("student.selectAll");
		studentSelectByIdSql = sql.getValue("student.selectById");
		studentSelectByStudentAndCourseSql = sql.getValue("student.selectByStudentAndCourse");
		studentSelectCountSql = sql.getValue("student.selectCount");
		studentUpdateByIdSql = sql.getValue("student.updateById");
		studentDeleteByIdSql = sql.getValue("student.deleteById");
		studentCourseInsertSql = sql.getValue("student_course.insert");
		studentCoursedeleteByCourseIdNotINAndStudentIdSql = sql.getValue("student_course.deleteByCourseIdNotINAndStudentId");
	}

	@Override
    public Student create(Student student) {
        var keyHolder = new KeyHolder();
        var params = List.of(
        			studentIdPrefix,
        			student.getName(),
        			student.getDateOfBirth(),
        			student.getGender(),
        			student.getPhone(),
        			student.getEducation()
        		);
        jdbc.insert(studentInsertSql, params, keyHolder);
        
        var studentId = keyHolder.getKey().intValue();
        var studentCourseParams = new ArrayList<List<?>>(student.getCourses().size());
        
        for (var course : student.getCourses()) {
        	studentCourseParams.add(List.of(studentId, course.getIdCode()));
        }
        
        jdbc.batchUpdate(studentCourseInsertSql, studentCourseParams);
        
        var studentInserted = jdbc.queryForObject(studentSelectByIdSql, List.of(studentIdPrefix + studentId), studentRowMapper);
        var courses = courseRepo.findByStudentId(studentInserted.getId());
        studentInserted.setCourses(courses);
        
        return studentInserted;
    }

	@Override
    public boolean update(Student student) {
        var studentParams = List.of(
        			student.getName(),
        			student.getDateOfBirth(),
        			student.getGender(),
        			student.getPhone(),
        			student.getEducation(),
        			student.getId()
        		);
        
        var updated = jdbc.update(studentUpdateByIdSql, studentParams) > 0;
        
        if (!updated) {
        	return false;
        }
        
        var courseIdBuilder = new StringBuffer();
        var first = true;
        for (var c : student.getCourses()) {
        	if (!first) {
        		courseIdBuilder.append(", ");
        	}
        	courseIdBuilder.append(c.getId());
        	first = false;
        }
        
        jdbc.update(studentCoursedeleteByCourseIdNotINAndStudentIdSql, List.of(courseIdBuilder.toString(), student.getIdCode()));
        
        var studentCourseParams = new ArrayList<List<?>>(student.getCourses().size());
        
        for (var course : student.getCourses()) {
        	studentCourseParams.add(List.of(student.getIdCode(), course.getIdCode()));
        }
        
        jdbc.batchUpdate(studentCourseInsertSql, studentCourseParams);
        
        var studentUpdated = jdbc.queryForObject(studentSelectByIdSql, List.of(student.getId()), studentRowMapper);
        var courses = courseRepo.findByStudentId(studentUpdated.getId());
        studentUpdated.setCourses(courses);
        return true;
    }

	@Override
    public boolean deleteById(String id) {
        return jdbc.update(studentDeleteByIdSql, List.of(id)) > 0;
    }

    @Override
    public Student findById(String id) {
        var student = jdbc.queryForObject(studentSelectByIdSql, List.of(id), studentRowMapper);
        var courses = courseRepo.findByStudentId(student.getId());
        student.setCourses(courses);
        return student;
    }

    @Override
    public List<Student> findAll() {
        var students = jdbc.queryForList(studentSelectAllSql, null, studentRowMapper);
        
        for (var student : students) {
        	student.setCourses(courseRepo.findByStudentId(student.getId()));
        }
        return students;
    }

    @Override
    public List<Student> search(String studentKeyword, String courseKeyword) {
    	
    	var params = List.of(
    				"%" + studentKeyword + "%",
    				"%" + studentKeyword + "%",
    				"%" + studentKeyword + "%",
    				"%" + studentKeyword + "%",
    				"%" + courseKeyword + "%",
    				"%" + courseKeyword + "%",
    				"%" + courseKeyword + "%"
    			);
    	
        var students = jdbc.queryForList(studentSelectByStudentAndCourseSql, params, studentRowMapper);
        
        for (var student : students) {
        	student.setCourses(courseRepo.findByStudentId(student.getId()));
        }
        
        return students;
    }

    @Override
    public long getCount() {
        return jdbc.queryForObject(studentSelectCountSql, null, Long.class);
    }

}