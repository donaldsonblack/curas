package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.checklists.ChecklistDetail;

import dev.donaldsonblack.cura.model.checklists.Checklist;
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
public class ChecklistRepository {

	private final NamedParameterJdbcTemplate jdbc;
	private final ObjectMapper objectMapper;

	@Autowired
	public ChecklistRepository(NamedParameterJdbcTemplate jdbc, ObjectMapper objectMapper) {
		this.jdbc = jdbc;
		this.objectMapper = objectMapper;
	}

	private final RowMapper<Checklist> checklistMapper = new RowMapper<>() {
		@Override
		public Checklist mapRow(ResultSet rs, int rowNum) throws SQLException {
			Checklist c = new Checklist();
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			c.setDescription(rs.getString("description"));
			c.setType(rs.getString("type"));
			c.setDepartmentId(rs.getInt("department_id"));
			c.setEquipmentId(rs.getInt("equipment_id"));
			c.setAuthorId(rs.getInt("author_id"));
			c.setCreated(rs.getTimestamp("created").toInstant());

			try {
				String json = rs.getString("questions");
				if (json != null) {
					JsonNode node = objectMapper.readTree(json);
					c.setQuestions(node);
				}
			} catch (Exception e) {
				throw new SQLException("Failed to parse questions JSON", e);
			}
			return c;
		}
	};

	public List<Checklist> findAll() {
		String sql = "SELECT * FROM checklists ORDER BY created DESC";
		return jdbc.query(sql, checklistMapper);
	}

	public Optional<Checklist> findById(Integer id) {
		String sql = "SELECT * FROM checklists WHERE id = :id";
		try {
			return Optional.ofNullable(
					jdbc.queryForObject(sql, Map.of("id", id), checklistMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Checklist> getByEquipmentId(Integer equipmentId) {
		String sql = "SELECT * FROM checklists WHERE equipment_id = :equipmentId ORDER BY created DESC";
		return jdbc.query(sql, Map.of("equipmentId", equipmentId), checklistMapper);
	}

	public Checklist insert(Checklist checklist) {
		String sql = """
				INSERT INTO checklists
				(name, description, type, department_id, equipment_id, author_id, created, questions)
				VALUES (:name, :description, :type, :departmentId, :equipmentId, :authorId, COALESCE(:created, now()), CAST(:questions AS jsonb))
				RETURNING *
				""";

		Map<String, Object> params = new HashMap<>();
		params.put("name", checklist.getName());
		params.put("description", checklist.getDescription());
		params.put("type", checklist.getType());
		params.put("departmentId", checklist.getDepartmentId());
		params.put("equipmentId", checklist.getEquipmentId());
		params.put("authorId", checklist.getAuthorId());
		params.put("created", checklist.getCreated());
		params.put("questions", checklist.getQuestions() != null ? checklist.getQuestions().toString() : "{}");

		return jdbc.queryForObject(sql, params, checklistMapper);
	}

	public boolean update(Checklist checklist) {
		String sql = """
				UPDATE checklists
				SET name = :name,
				    description = :description,
				    type = :type,
				    department_id = :departmentId,
				    equipment_id = :equipmentId,
				    author_id = :authorId,
				    questions = CAST(:questions AS jsonb)
				WHERE id = :id
				""";

		Map<String, Object> params = new HashMap<>();
		params.put("id", checklist.getId());
		params.put("name", checklist.getName());
		params.put("description", checklist.getDescription());
		params.put("type", checklist.getType());
		params.put("departmentId", checklist.getDepartmentId());
		params.put("equipmentId", checklist.getEquipmentId());
		params.put("authorId", checklist.getAuthorId());
		params.put("questions", checklist.getQuestions() != null ? checklist.getQuestions().toString() : "{}");

		return jdbc.update(sql, params) > 0;
	}

	public boolean deleteById(Integer id) {
		String sql = "DELETE FROM checklists WHERE id = :id";
		return jdbc.update(sql, Map.of("id", id)) > 0;
	}

	// helper inside repository
	private JsonNode parseJson(String json) {
		try {
			return json != null ? objectMapper.readTree(json) : objectMapper.createObjectNode();
		} catch (Exception ex) {
			throw new RuntimeException("Failed to parse questions json", ex);
		}
	}

	private String getNullable(ResultSet rs, String col) throws SQLException {
		String s = rs.getString(col);
		return (rs.wasNull() ? null : s);
	}

	public Optional<ChecklistDetail> findDetailById(Integer id) {
		String sql = """
				SELECT
				  c.id, c.name, c.description, c.type, c.created, c.questions,
				  e.id   AS equipment_id,
				  e.name AS equipment_name,
				  e.model AS equipment_model,
				  e.serial AS equipment_serial,
				  d.id   AS department_id,
				  d.name AS department_name,
				  u.id   AS author_id,
				  u.name AS author_name
				FROM checklists c
				JOIN equipment  e ON e.id = c.equipment_id
				JOIN department d ON d.id = c.department_id     -- <== singular
				JOIN users      u ON u.id = c.author_id
				WHERE c.id = :id
				""";
		try {
			return Optional.ofNullable(
					jdbc.queryForObject(sql, Map.of("id", id), (rs, n) -> new ChecklistDetail.Builder()
							.id(rs.getInt("id"))
							.name(rs.getString("name"))
							.description(rs.getString("description"))
							.type(rs.getString("type"))
							.created(rs.getTimestamp("created").toInstant())
							.questions(parseJson(rs.getString("questions")))
							.equipmentId(rs.getInt("equipment_id"))
							.equipmentName(rs.getString("equipment_name"))
							.equipmentModel(rs.getString("equipment_model"))
							.equipmentSerial(getNullable(rs, "equipment_serial"))
							.departmentId(rs.getInt("department_id"))
							.departmentName(rs.getString("department_name"))
							.authorId(rs.getInt("author_id"))
							.author(rs.getString("author_name"))
							.build()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<ChecklistDetail> findDetails() {
		String sql = """
				SELECT
				  c.id, c.name, c.description, c.type, c.created, c.questions,
				  e.id   AS equipment_id,
				  e.name AS equipment_name,
				  e.model AS equipment_model,
				  e.serial AS equipment_serial,
				  d.id   AS department_id,
				  d.name AS department_name,
				  u.id   AS author_id,
				  u.name AS author_name
				FROM checklists c
				JOIN equipment  e ON e.id = c.equipment_id
				JOIN department d ON d.id = c.department_id     -- <== singular
				JOIN users      u ON u.id = c.author_id
				ORDER BY c.created DESC, c.id DESC
				""";

		return jdbc.query(sql, (rs, n) -> new ChecklistDetail.Builder()
				.id(rs.getInt("id"))
				.name(rs.getString("name"))
				.description(rs.getString("description"))
				.type(rs.getString("type"))
				.created(rs.getTimestamp("created").toInstant())
				.questions(parseJson(rs.getString("questions")))
				.equipmentId(rs.getInt("equipment_id"))
				.equipmentName(rs.getString("equipment_name"))
				.equipmentModel(rs.getString("equipment_model"))
				.equipmentSerial(getNullable(rs, "equipment_serial"))
				.departmentId(rs.getInt("department_id"))
				.departmentName(rs.getString("department_name"))
				.authorId(rs.getInt("author_id"))
				.author(rs.getString("author_name"))
				.build());
	}
}
