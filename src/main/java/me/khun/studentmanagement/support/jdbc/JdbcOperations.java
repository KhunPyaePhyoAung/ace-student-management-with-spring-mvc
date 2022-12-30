package me.khun.studentmanagement.support.jdbc;

import java.util.List;

public interface JdbcOperations {
	
	boolean insert(String sql, List<?> params);
	
	boolean insert(String sql, List<?> params, KeyHolder keyHolder);

	int update(String sql, List<?> parameters);
	
	int[] batchUpdate(String sql, List<List<?>> paramList);
	
	<T> T queryForObject(String sql, List<?> parameters, RowMapper<T> rowMapper);
	
	<T> T queryForObject(String sql, List<?> parameters, Class<? extends T> clazz);
	
	<T> List<T> queryForList(String sql, List<?> parameters, RowMapper<T> rowMapper);
}
