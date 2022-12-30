package me.khun.studentmanagement.model.repo.impl;

import java.util.HashMap;
import java.util.List;

import me.khun.studentmanagement.application.Application;
import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.model.repo.CourseRepo;
import me.khun.studentmanagement.model.repo.exception.DataAccessException;
import me.khun.studentmanagement.util.MyBatisUtil;

public class MyBatisCourseRepoImpl implements CourseRepo {
	
	private String courseIdPrefix;
	
	public MyBatisCourseRepoImpl() {
		courseIdPrefix = Application.COURSE_ID_PREFIX;
	}

	@Override
	public Course create(Course course) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			var param = new HashMap<String, Object>();
			param.put("course", course);
			param.put("courseIdPrefix", courseIdPrefix);
			
			session.insert("course.insert", param);
			var createdCourse = (Course) session.selectOne("course.selectById", courseIdPrefix + course.getIdCode());
			session.commit();
			return createdCourse;
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
	public boolean update(Course course) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			session.update("course.updateById", course);
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
			session.delete("course.deleteById", id);
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
	public Course findById(String id) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectOne("course.selectById", id);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Course> findAll() {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectList("course.selectAll");
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
			return session.selectOne("course.selectCount");
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Course> findByStudentId(String studentId) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectList("course.selectByStudentId", studentId);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Course> search(String keyword) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectList("course.selectByKeywordLike", "%" + keyword + "%");
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
