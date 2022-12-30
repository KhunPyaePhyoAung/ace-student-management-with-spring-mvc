package me.khun.studentmanagement.application;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import me.khun.studentmanagement.model.repo.CourseRepo;
import me.khun.studentmanagement.model.repo.StudentRepo;
import me.khun.studentmanagement.model.repo.UserRepo;
import me.khun.studentmanagement.model.repo.impl.MyBatisCourseRepoImpl;
import me.khun.studentmanagement.model.repo.impl.MyBatisStudentRepoImpl;
import me.khun.studentmanagement.model.repo.impl.MyBatisUserRepoImpl;
import me.khun.studentmanagement.model.service.AuthService;
import me.khun.studentmanagement.model.service.CourseService;
import me.khun.studentmanagement.model.service.StudentService;
import me.khun.studentmanagement.model.service.UserService;
import me.khun.studentmanagement.model.service.impl.AuthServiceImpl;
import me.khun.studentmanagement.model.service.impl.CourseServiceImpl;
import me.khun.studentmanagement.model.service.impl.StudentServiceImpl;
import me.khun.studentmanagement.model.service.impl.UserServiceImpl;
import me.khun.studentmanagement.support.jdbc.JdbcOperations;
import me.khun.studentmanagement.support.jdbc.JdbcTemplate;
import me.khun.studentmanagement.tool.PropertyReader;

public class Application {
	
	public static final String USER_ID_PREFIX = "USR";
	public static final String STUDENT_ID_PREFIX = "STU";
	public static final String COURSE_ID_PREFIX = "CUR";
	public static final String DATABASE_INFO_LOCATION = "/database-info.properties";
	public static final String SQL_PROPERTIES_LOCATION = "/sql.properties";
	
	public static PropertyReader getSqlPropertyReader() {
		return new PropertyReader(SQL_PROPERTIES_LOCATION);
	}
	
	public static DataSource getDataSource() {
		var reader = new PropertyReader(DATABASE_INFO_LOCATION);
		var dataSource = new MysqlDataSource();
		dataSource.setUrl(reader.getValue("database.url"));
		dataSource.setUser(reader.getValue("database.user"));
		dataSource.setPassword(reader.getValue("database.password"));
		return dataSource;
	}
	
	public static JdbcOperations getJdbcOperations() {
		return new JdbcTemplate(getDataSource());
	}

	public static CourseRepo getCourseRepo() {
		return new MyBatisCourseRepoImpl();
	}
	
	public static UserRepo getUserRepo() {
		return new MyBatisUserRepoImpl();
	}
	
	public static StudentRepo getStudentRepo() {
		return new MyBatisStudentRepoImpl();
	}
	
	public static UserService getUserService() {
		return new UserServiceImpl(getUserRepo());
	}
	
	public static CourseService getCourseService() {
		return new CourseServiceImpl(getCourseRepo());
	}
	
	public static StudentService getStudentService() {
		return new StudentServiceImpl(getStudentRepo());
	}
	
	public static AuthService getAuthService() {
		return new AuthServiceImpl(getUserRepo());
	}
}
