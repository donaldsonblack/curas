package dev.donaldsonblack.cura.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public abstract class JdbcGenericRepository<T, ID> {

	protected final JdbcTemplate jdbcTemplate;

	public JdbcGenericRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	protected abstract String getTableName();

	protected abstract String getIdColumn();

	protected abstract RowMapper<T> getRowMapper();

	protected abstract Object[] getInsertParams(T entity);

	protected abstract Object[] getUpdateParams(T entity);

	public List<T> findAll() {
		return jdbcTemplate.query("SELECT * FROM " + getTableName(), getRowMapper());
	}

	public Optional<T> findById(ID id) {
		String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
		List<T> results = jdbcTemplate.query(sql, getRowMapper(), id);
		return results.stream().findFirst();
	}

	public boolean deleteById(ID id) {
		String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
		return jdbcTemplate.update(sql, id) > 0;
	}

	public T insert(T entity, String insertSql) {
		jdbcTemplate.update(insertSql, getInsertParams(entity));
		return entity;
	}

	public boolean update(T entity, String updateSql) {
		return jdbcTemplate.update(updateSql, getUpdateParams(entity)) > 0;
	}
}
