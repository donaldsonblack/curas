package dev.donaldsonblack.cura.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import dev.donaldsonblack.cura.model.Checklist;

@Repository
public class ChecklistRepository extends JdbcGenericRepository<Checklist, Integer> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public ChecklistRepository(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	protected String getTableName() {
		return "checklists";
	}

	@Override
	protected String getIdColumn() {
		return "id";
	}

	@Override
	protected RowMapper<Checklist> getRowMapper() {
		return (rs, rowNum) -> {
			Checklist c = new Checklist();
			c.setId(rs.getInt("id"));
			c.setType(rs.getString("type"));

			// Nullable FKs (ON DELETE SET NULL)
			Integer deptId = (Integer) rs.getObject("department_id");
			Integer equipId = (Integer) rs.getObject("equipment_id");
			Integer authorId = (Integer) rs.getObject("author_id");
			c.setDepartmentId(deptId);
			c.setEquipmentId(equipId);
			c.setAuthor(authorId == null ? null : String.valueOf(authorId));

			Timestamp ts = rs.getTimestamp("created");
			c.setCreated(ts == null ? null : ts.toInstant());

			// Required text fields
			c.setDescription(rs.getString("description"));
			c.setName(rs.getString("name"));

			String q = rs.getString("questions");
			try {
				JsonNode node = (q == null || q.isBlank())
						? objectMapper.readTree("[]")
						: objectMapper.readTree(q);
				c.setQuestions(node);
			} catch (Exception e) {
				throw new RuntimeException("Invalid JSON for checklist.questions", e);
			}
			return c;
		};
	}

	/** Serialize JsonNode (null -> "[]"). */
	private String toJson(JsonNode node) {
		try {
			return node == null ? "[]" : objectMapper.writeValueAsString(node);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize questions JSON", e);
		}
	}

	/**
	 * Order must match the INSERT column list.
	 * (type, department_id, equipment_id, author_id, created, questions,
	 * description, name)
	 */
	@Override
	protected Object[] getInsertParams(Checklist c) {
		return new Object[] {
				c.getType(),
				c.getDepartmentId(), // department_id
				c.getEquipmentId(), // equipment_id
				c.getAuthor() == null ? null : Integer.valueOf(c.getAuthor()), // author_id
				c.getCreated() == null ? null : Timestamp.from(c.getCreated()), // created
				toJson(c.getQuestions()), // questions::jsonb
				c.getDescription() == null ? "" : c.getDescription(), // description (NOT NULL in DB)
				c.getName() == null ? "" : c.getName() // name (NOT NULL in DB)
		};
	}

	/**
	 * Order must match UPDATE SET list (+ id at end).
	 * We don't update 'created' here.
	 */
	@Override
	protected Object[] getUpdateParams(Checklist c) {
		return new Object[] {
				c.getType(),
				c.getDepartmentId(),
				c.getEquipmentId(),
				c.getAuthor() == null ? null : Integer.valueOf(c.getAuthor()),
				toJson(c.getQuestions()),
				c.getDescription() == null ? "" : c.getDescription(),
				c.getName() == null ? "" : c.getName(),
				c.getId()
		};
	}

	/** Create a checklist (JSONB questions) and return the created row. */
	public Checklist insert(Checklist checklist) {
		// Include description + name in the column list and values
		String sql = """
				INSERT INTO checklists
				    (type, department_id, equipment_id, author_id, created, questions, description, name)
				VALUES
				    (?, ?, ?, ?, COALESCE(?, now()), ?::jsonb, ?, ?)
				""";
		return super.insert(checklist, sql);
	}

	public boolean update(Checklist checklist) {
		String sql = """
				UPDATE checklists
				   SET type = ?,
				       department_id = ?,
				       equipment_id = ?,
				       author_id = ?,
				       questions = ?::jsonb,
				       description = ?,
				       name = ?
				 WHERE id = ?
				""";
		return super.update(checklist, sql);
	}

	public List<Checklist> getByEquipmentId(Integer equipmentId) {
		String sql = "SELECT * FROM checklists WHERE equipment_id = ?";
		return jdbcTemplate.query(sql, getRowMapper(), equipmentId);
	}

	public Optional<Checklist> findById(Integer id) {
		return super.findById(id);
	}

	public boolean deleteById(Integer id) {
		return super.deleteById(id);
	}
}
