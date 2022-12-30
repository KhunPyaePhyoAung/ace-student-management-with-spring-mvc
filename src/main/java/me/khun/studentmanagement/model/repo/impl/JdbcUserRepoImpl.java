package me.khun.studentmanagement.model.repo.impl;

import java.util.List;

import me.khun.studentmanagement.application.Application;
import me.khun.studentmanagement.model.entity.User;
import me.khun.studentmanagement.model.repo.UserRepo;
import me.khun.studentmanagement.model.repo.rowmapper.UserRowMapper;
import me.khun.studentmanagement.support.jdbc.JdbcOperations;
import me.khun.studentmanagement.support.jdbc.KeyHolder;
import me.khun.studentmanagement.support.jdbc.RowMapper;

public class JdbcUserRepoImpl implements UserRepo {

	private JdbcOperations jdbc;
	private String userIdPrefix;
	private RowMapper<User> userRowMapper;
	
	private String userInsertSql;
	private String userSelectAllSql;
	private String userSelectByIdSql;
	private String userSelectByEmailAndPasswordSql;
	private String userSelectByKeywordLikeSql;
	private String userSelectByKeywordLikeAndApprovedSql;
	private String userSelectCountSql;
	private String userUpdateByIdSql;
	private String userSelectCountByApprovedSql;
	private String userDeleteByIdSql;
	
	public JdbcUserRepoImpl() {
		jdbc = Application.getJdbcOperations();
		userIdPrefix = Application.USER_ID_PREFIX;
		userRowMapper = new UserRowMapper();
		var sql = Application.getSqlPropertyReader();
		
		userInsertSql = sql.getValue("user.insert");
		userSelectAllSql = sql.getValue("user.selectAll");
		userSelectByIdSql = sql.getValue("user.selectById");
		userSelectByEmailAndPasswordSql = sql.getValue("user.selectByEmailAndPassword");
		userSelectByKeywordLikeSql = sql.getValue("user.selectByKeywordLike");
		userSelectByKeywordLikeAndApprovedSql = sql.getValue("user.selectByKeywordLikeAndApproved");
		userSelectCountSql = sql.getValue("user.selectCount");
		userSelectCountByApprovedSql = sql.getValue("user.selectCountByApproved");
		userUpdateByIdSql = sql.getValue("user.updateById");
		userDeleteByIdSql = sql.getValue("user.deleteById");
	}
	@Override
    public User create(User user) {
        var keyHolder = new KeyHolder();
        var params = List.of(
    				userIdPrefix,
	        		user.getName(),
	        		user.getEmail(),
	        		user.getPassword(),
	        		user.getRole(),
	        		user.isApproved()
	        	);
        jdbc.insert(userInsertSql, params, keyHolder);
        return jdbc.queryForObject(userSelectByIdSql, List.of(userIdPrefix + keyHolder.getKey().intValue()), userRowMapper);
    }

	@Override
    public boolean update(User user) {
		var params = List.of(
					user.getName(),
					user.getEmail(),
					user.getPassword(),
					user.getRole(),
					user.isApproved(),
					user.getId()
				);
        return jdbc.update(userUpdateByIdSql, params) > 0;
    }

	@Override
    public boolean deleteById(String id) {
        return jdbc.update(userDeleteByIdSql, List.of(id)) > 0;
    }

	@Override
    public User findById(String id) {
        return jdbc.queryForObject(userSelectByIdSql, List.of(id), userRowMapper);
    }
	
	@Override
	public User findByEmail(String email) {
		throw new UnsupportedOperationException();
	}

	@Override
    public List<User> findAll() {
        return jdbc.queryForList(userSelectAllSql, null, userRowMapper);
    }

	@Override
    public List<User> search(String keyword) {
		var params = List.of(
					"%" + keyword + "%",
					"%" + keyword + "%"
				);
        return jdbc.queryForList(userSelectByKeywordLikeSql, params, userRowMapper);
    }
	
	@Override
	public List<User> search(String keyword, boolean approved) {
		var params = List.of(
				"%" + keyword + "%",
				"%" + keyword + "%",
				approved
			);
    return jdbc.queryForList(userSelectByKeywordLikeAndApprovedSql, params, userRowMapper);
	}

	@Override
    public User findByEmailAndPassword(String email, String password) {
        return jdbc.queryForObject(userSelectByEmailAndPasswordSql, List.of(email, password), userRowMapper);
    }

	@Override
    public long getCount() {
        return jdbc.queryForObject(userSelectCountSql, null, Long.class);
    }
	
	@Override
	public long getCount(boolean approved) {
		return jdbc.queryForObject(userSelectCountByApprovedSql, List.of(approved), Long.class);
	}

}