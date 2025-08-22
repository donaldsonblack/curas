package dev.donaldsonblack.cura.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
	private final JdbcTemplate jdbcTemplate;

	public UserService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void ensureUserExists(UUID id, String name) {
		String sql = "INSERT INTO users (id, name) VALUES (?, ?) ON CONFLICT DO NOTHING";
		jdbcTemplate.update(sql, id, name);
	}

}
