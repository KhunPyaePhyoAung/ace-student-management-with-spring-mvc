package me.khun.studentmanagement.support.jdbc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import me.khun.studentmanagement.model.repo.exception.DataAccessException;

public class JdbcTemplate implements JdbcOperations {
	
	private DataSource dataSource;
	
	public JdbcTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public boolean insert(String sql, List<?> params) {
		return insert(sql, params, null);
	}
	
	@Override
	public boolean insert(String sql, List<?> params, KeyHolder keyHolder) {
		try (
			var connection = dataSource.getConnection();
			var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		){
			
			setupParameters(stmt, params);
			if (stmt.executeUpdate() > 0) {
				var rs = stmt.getGeneratedKeys();
				if (keyHolder != null && rs.next()) {
					keyHolder.setKey(BigDecimal.valueOf(rs.getLong(1)));
				}
				return true;
			}
			
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public int update(String sql, List<?> parameters) {
		try (
			var connection = dataSource.getConnection();
			var stmt = connection.prepareStatement(sql);
		) {
			setupParameters(stmt, parameters);
			
			return stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
	
	@Override
	public int[] batchUpdate(String sql, List<List<?>> paramList) {
		try (
			var connection = dataSource.getConnection();
			var stmt = connection.prepareStatement(sql);
		) {
			for (var parameters : paramList) {
				setupParameters(stmt, parameters);
				stmt.addBatch();
			}
			return stmt.executeBatch();
			
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T queryForObject(String sql, List<?> parameters, RowMapper<T> rowMapper) {
		try (
			var connection = dataSource.getConnection();
			var stmt = connection.prepareCall(sql);
		) {
			setupParameters(stmt, parameters);
			
			var rs = stmt.executeQuery();
			if (rs.next()) {
				return rowMapper.mapRow(rs, 1);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public <T> T queryForObject(String sql, List<?> parameters, Class<? extends T> clazz) {
		try (
			var connection = dataSource.getConnection();
			var stmt = connection.prepareCall(sql);
		) {
			setupParameters(stmt, parameters);
			
			var rs = stmt.executeQuery();
			return parseResultSet(rs, clazz);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> List<T> queryForList(String sql, List<?> parameters, RowMapper<T> rowMapper) {
		try (
			var connection = dataSource.getConnection();
			var stmt = connection.prepareCall(sql);
		) {
			setupParameters(stmt, parameters);
			
			var rs = stmt.executeQuery();
			var count = 1;
			var list = new ArrayList<T>(rs.getMetaData().getColumnCount());
			while (rs.next()) {
				list.add(rowMapper.mapRow(rs, count++));
			}
			return list;
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}
	
	private void setupParameters(PreparedStatement stmt, List<?> params) throws SQLException {
		if (params == null) {
			return;
		}
		
		for (int i = 1; i <= params.size(); i++) {
			var value = params.get(i - 1);
			
			if (value instanceof Byte v) {
				stmt.setByte(i, v);
			} else if (value instanceof Short v) {
				stmt.setShort(i, v);
			} else if (value instanceof Integer v) {
				stmt.setInt(i, v);
			} else if (value instanceof Long v) {
				stmt.setLong(i, v);
			} else if (value instanceof Float v) {
				stmt.setFloat(i, v);
			} else if(value instanceof Double v) {
				stmt.setDouble(i, v);
			} else if (value instanceof BigDecimal v) {
				stmt.setBigDecimal(i, v);
			} else if (value instanceof Date v) {
				stmt.setDate(i, v);
			} else if (value instanceof LocalDate v) {
				stmt.setDate(i, Date.valueOf(v));
			} else if (value instanceof String v) {
				stmt.setString(i, v);
			} else if (value instanceof Boolean v) {
				stmt.setBoolean(i, v);
			} else if (value instanceof Enum<?> v) {
				stmt.setString(i, v.name());
			} else {
				stmt.setObject(i, value);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parseResultSet(ResultSet rs, Class<? extends T> clazz) throws SQLException {
		if (!rs.next()) {
			return null;
		}
		
		if (clazz.isAssignableFrom(Byte.class)) {
			return (T) ((Byte) rs.getByte(1));
		} else if (clazz.isAssignableFrom(Short.class)) {
			return (T) ((Short) rs.getShort(1));
		} else if (clazz.isAssignableFrom(Integer.class)) {
			return (T) ((Integer) rs.getInt(1));
		} else if (clazz.isAssignableFrom(Long.class)) {
			return (T) ((Long) rs.getLong(1));
		} else if (clazz.isAssignableFrom(Float.class)) {
			return (T) ((Float) rs.getFloat(1));
		} else if (clazz.isAssignableFrom(Double.class)) {
			return (T) ((Double) rs.getDouble(1));
		} else if (clazz.isAssignableFrom(BigDecimal.class)) {
			return (T) ((BigDecimal) rs.getBigDecimal(1));
		} else if (clazz.isAssignableFrom(Date.class)) {
			return (T) ((Date) rs.getDate(1));
		} else if (clazz.isAssignableFrom(LocalDate.class)) {
			return (T) ((LocalDate) rs.getDate(1).toLocalDate());
		} else if (clazz.isAssignableFrom(String.class)) {
			return (T) ((String) rs.getString(1));
		} else if (clazz.isAssignableFrom(Boolean.class)) {
			return (T) ((Boolean) rs.getBoolean(1));
		} else {
			return (T) ((Object) rs.getObject(1));
		}
	}

}
