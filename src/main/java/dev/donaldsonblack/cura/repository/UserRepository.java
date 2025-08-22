package dev.donaldsonblack.cura.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.donaldsonblack.cura.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository extends JdbcGenericRepository<User, Integer> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected String getIdColumn() {
        return "id";
    }

    @Override
    protected RowMapper<User> getRowMapper() {
        return (rs, row) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setCognitoSub(UUID.fromString(rs.getString("cognito_sub")));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            // departments is a JSONB column; parse as needed
            try {
                user.setDepartments(objectMapper.readTree(rs.getString("departments")));
            } catch (Exception e) {
                throw new RuntimeException("Error parsing departments JSON: " + e.getMessage(), e);
            }
            user.setRole(rs.getString("role"));
            user.setCreatedAt(rs.getTimestamp("created_at").toInstant());
            user.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
            return user;
        };
    }

    @Override
    protected Object[] getInsertParams(User user) {
        return new Object[]{
            user.getId(),
            user.getCognitoSub(),
            user.getName(),
            user.getEmail(),
            user.getDepartments().toString(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        };
    }

    @Override
    protected Object[] getUpdateParams(User user) {
        return new Object[]{
            user.getCognitoSub(),
            user.getName(),
            user.getEmail(),
            user.getDepartments().toString(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.getId()
        };
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, getRowMapper(), email);
        return users.stream().findFirst();
    }

    public User insert(User user) {
        String sql = """
            INSERT INTO users (id, cognito_sub, name, email, departments, role, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?::jsonb, ?, ?, ?)
        """;
        return super.insert(user, sql);
    }

    public boolean update(User user) {
        String sql = """
            UPDATE users
            SET cognito_sub = ?, name = ?, email = ?, departments = ?::jsonb, role = ?, created_at = ?, updated_at = ?
            WHERE id = ?
        """;
        return super.update(user, sql);
    }
}
