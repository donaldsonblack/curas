package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.users.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserRepository {

	private final NamedParameterJdbcTemplate jdbc;
	private final ObjectMapper objectMapper;

	@Autowired
	public UserRepository(NamedParameterJdbcTemplate jdbc, ObjectMapper objectMapper) {
		this.jdbc = jdbc;
		this.objectMapper = objectMapper;
	}

	// RowMapper
	private final RowMapper<User> USER_MAPPER = new RowMapper<>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User u = new User();
			int id = rs.getInt("id");
			u.setId(rs.wasNull() ? null : id);
			u.setCognitoSub((java.util.UUID) rs.getObject("cognito_sub"));
			u.setName(rs.getString("name"));
			u.setEmail(rs.getString("email"));
			u.setRole(rs.getString("role"));

			String deps = rs.getString("departments");
			try {
				JsonNode depsNode = deps != null ? objectMapper.readTree(deps)
						: objectMapper.createArrayNode();
				u.setDepartments(depsNode);
			} catch (Exception e) {
				throw new SQLException("Failed to parse departments JSON", e);
			}

			var ts = rs.getTimestamp("created");
			u.setCreated(ts != null ? ts.toInstant() : null);
			return u;
		}
	};

	// CRUD
	public List<User> findAll() {
		String sql = """
				SELECT id, cognito_sub, name, email, departments, role, created
				  FROM users
				 ORDER BY id DESC
				""";
		return jdbc.query(sql, USER_MAPPER);
	}

	public Optional<User> findById(Integer id) {
		String sql = """
				SELECT id, cognito_sub, name, email, departments, role, created
				  FROM users
				 WHERE id = :id
				""";
		try {
			return Optional.ofNullable(jdbc.queryForObject(sql, Map.of("id", id), USER_MAPPER));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByCognitoSub(UUID sub) {
		String sql = """
				SELECT id, cognito_sub, name, email, departments, role, created
				  FROM users
				 WHERE cognito_sub = :sub
				""";
		try {
			return Optional.ofNullable(jdbc.queryForObject(sql, Map.of("sub", sub), USER_MAPPER));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public User insert(User u) {
		String sql = """
				INSERT INTO users (cognito_sub, name, email, departments, role, created)
				VALUES (:sub, :name, :email, CAST(:departments AS jsonb), COALESCE(:role, 'user'), COALESCE(:created, now()))
				RETURNING id, cognito_sub, name, email, departments, role, created
				""";
		Map<String, Object> p = new HashMap<>();
		p.put("sub", u.getCognitoSub());
		p.put("name", u.getName());
		p.put("email", u.getEmail());
		p.put("departments", u.getDepartments() != null ? u.getDepartments().toString() : "[]");
		p.put("role", u.getRole()); // may be null -> DB default 'user'
		p.put("created", u.getCreated()); // may be null -> DB default now()
		return jdbc.queryForObject(sql, p, USER_MAPPER);
	}

	public boolean update(User u) {
		String sql = """
				UPDATE users
				   SET cognito_sub = :sub,
				       name        = :name,
				       email       = :email,
				       departments = CAST(:departments AS jsonb),
				       role        = :role
				 WHERE id = :id
				""";
		Map<String, Object> p = new HashMap<>();
		p.put("id", u.getId());
		p.put("sub", u.getCognitoSub());
		p.put("name", u.getName());
		p.put("email", u.getEmail());
		p.put("departments", u.getDepartments() != null ? u.getDepartments().toString() : "[]");
		p.put("role", u.getRole());
		return jdbc.update(sql, p) > 0;
	}

	public boolean deleteById(Integer id) {
		String sql = "DELETE FROM users WHERE id = :id";
		return jdbc.update(sql, Map.of("id", id)) > 0;
	}
}
