package me.khun.studentmanagement.model.repo.impl;

import java.util.HashMap;
import java.util.List;

import me.khun.studentmanagement.application.Application;
import me.khun.studentmanagement.model.entity.Student;
import me.khun.studentmanagement.model.repo.StudentRepo;
import me.khun.studentmanagement.model.repo.exception.DataAccessException;
import me.khun.studentmanagement.util.MyBatisUtil;

public class MyBatisStudentRepoImpl implements StudentRepo {
	
	private String studentIdPrefix;
	
	public MyBatisStudentRepoImpl() {
		studentIdPrefix = Application.STUDENT_ID_PREFIX;
	}

	@Override
	public Student create(Student student) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			var studentInsertParam = new HashMap<String, Object>();
			studentInsertParam.put("studentIdPrefix", studentIdPrefix);
			studentInsertParam.put("student", student);
			
			session.insert("student.insert", studentInsertParam);
			
			var courseInsertParam = new HashMap<String, Object>();
			courseInsertParam.put("studentIdCode", student.getIdCode());
			courseInsertParam.put("courses", student.getCourses());
			
			session.insert("student.insertCourse", courseInsertParam);
			
			var createdStudent = (Student) session.selectOne("student.selectById", studentIdPrefix + student.getIdCode());
			session.commit();
			return createdStudent;
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean update(Student student) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			session.update("student.updateById", student);
			
			var deleteParam = new HashMap<String, Object>();
			deleteParam.put("courses", student.getCourses());
			deleteParam.put("studentIdCode", student.getIdCode());
			
			session.delete("student.deleteByCourseIdNotIn", deleteParam);
			
			var courseInsertParam = new HashMap<String, Object>();
			courseInsertParam.put("studentIdCode", student.getIdCode());
			courseInsertParam.put("courses", student.getCourses());
			
			session.insert("student.insertCourse", courseInsertParam);
			
			session.commit();
			return true;
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public boolean deleteById(String id) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			session.delete("student.deleteById", id);
			session.commit();
			return true;
		} catch (Exception e) {
			if (session != null) {
				session.rollback();
			}
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Student findById(String id) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectOne("student.selectById", id);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Student> findAll() {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectList("student.selectAll");
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public long getCount() {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectOne("student.selectCount");
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Student> search(String studentKeyword, String courseKeyword) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			var param = new HashMap<String, Object>();
			param.put("studentKeyword", "%" + studentKeyword + "%");
			param.put("courseKeyword", "%" + courseKeyword + "%");
			return session.selectList("student.selectByStudentKeywordAndCourseKeyword", param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
