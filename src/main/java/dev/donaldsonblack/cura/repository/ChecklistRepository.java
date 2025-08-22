package dev.donaldsonblack.cura.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
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
			c.setDepartmentId(rs.getInt("department_id"));
			c.setAuthor(rs.getObject("author_id").toString());
			c.setEquipmentId(rs.getInt("equipment_id"));
			c.setCreated(rs.getTimestamp("created").toInstant());
			try {
				c.setQuestions(objectMapper.readTree(rs.getString("questions")));
			} catch (Exception e) {
				throw new RuntimeException("Invalid JSON for checklist questions", e);
			}
			return c;
		};
	}

	@Override
	protected Object[] getInsertParams(Checklist c) {
		// return new Object[] {
		// c.getType(),
		// c.getDepartmentId(),
		// c.getEquipmentId(),
		// c.getAuthor(),
		//
		// }
		return new Object[] {
				c.getType(),
				c.getEquipmentId(),
				Integer.valueOf(c.getAuthor()),
				Timestamp.from(c.getCreated()),
				c.getQuestions().toString(),
				c.getDepartmentId()
		};
	}

	@Override
	protected Object[] getUpdateParams(Checklist c) {
		return new Object[] {
				c.getType(),
				c.getDepartmentId(),
				c.getEquipmentId(),
				Integer.valueOf(c.getAuthor()),
				c.getQuestions().toString(),
				c.getId()
		};
	}

	public Checklist insert(Checklist checklist) {
		String sql = """
					INSERT INTO checklists (type, equipment_id, author_id, created, questions, department_id)
					VALUES (?, ?, ?, ?, ?::jsonb, ?)
				""";
		return super.insert(checklist, sql);
	}

	public boolean update(Checklist checklist) {
		String sql = """
				    UPDATE checklists
				    SET type = ?, department_id = ?, equipment_id = ?, author_id = ?, questions = ?::jsonb
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
