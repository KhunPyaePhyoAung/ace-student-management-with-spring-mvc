package me.khun.studentmanagement.model.repo.impl;

import java.util.HashMap;
import java.util.List;

import me.khun.studentmanagement.application.Application;
import me.khun.studentmanagement.model.entity.User;
import me.khun.studentmanagement.model.repo.UserRepo;
import me.khun.studentmanagement.model.repo.exception.DataAccessException;
import me.khun.studentmanagement.util.MyBatisUtil;

public class MyBatisUserRepoImpl implements UserRepo {
	
	private String userIdPrefix;
	
	public MyBatisUserRepoImpl() {
		userIdPrefix = Application.USER_ID_PREFIX;
	}

	@Override
	public User create(User user) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			var param = new HashMap<String, Object>();
			param.put("user", user);
			param.put("userIdPrefix", userIdPrefix);
			
			session.insert("user.insert", param);
			var createdUser =  (User) session.selectOne("user.selectById", userIdPrefix + user.getIdCode());
			session.commit();
			return createdUser;
			
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
	public boolean update(User user) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			session.insert("user.updateById", user);
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
			session.delete("user.deleteById", id);
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
	public User findById(String id) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectOne("user.selectById", id);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public User findByEmail(String email) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectOne("user.selectByEmail", email);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<User> findAll() {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectList("user.selectAll");
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
			return session.selectOne("user.selectCount");
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<User> search(String keyword) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectList("user.selectByKeywordLike", "%" + keyword + "%");
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<User> search(String keyword, boolean approved) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			var param = new HashMap<String, Object>();
			param.put("keyword", "%" + keyword + "%");
			param.put("approved", approved);
			
			return session.selectList("user.selectByKeywordLikeAndApproved", param);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			var param = new HashMap<String, Object>();
			param.put("email", email);
			param.put("password", password);
			
			return session.selectOne("user.selectByEmailAndPassword", param);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public long getCount(boolean approved) {
		var session = MyBatisUtil.getSessionFactory().openSession();
		try {
			return session.selectOne("user.selectCountByApproved", approved);
		} catch (Exception e) {
			throw new DataAccessException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
