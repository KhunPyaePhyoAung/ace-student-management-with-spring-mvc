package me.khun.studentmanagement.model.repo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import me.khun.studentmanagement.model.entity.User;
import me.khun.studentmanagement.model.entity.User.Role;
import me.khun.studentmanagement.support.jdbc.RowMapper;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int index) throws SQLException {
		var user = new User();
		user.setId(rs.getString("id"));
		user.setIdPrefix(rs.getString("id_prefix"));
		user.setIdCode(rs.getInt("id_code"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setRole(Role.valueOf(rs.getString("role")));
		user.setApproved(rs.getBoolean("approved"));
		return user;
	}

}
