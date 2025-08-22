package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordRepository extends JdbcGenericRepository<Record, Integer> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	public RecordRepository(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	protected String getTableName() {
		return "record";
	}

	@Override
	protected String getIdColumn() {
		return "id";
	}

	@Override
	protected RowMapper<Record> getRowMapper() {
		return (rs, row) -> {
			Record r = new Record();
			r.setId(rs.getInt("id"));
			r.setChecklistId(rs.getInt("checklist_id"));
			r.setAuthorId(rs.getInt("author_id"));
			r.setCreated(rs.getTimestamp("created").toInstant());
			try {
				r.setAnswers(objectMapper.readTree(rs.getString("answers")));
			} catch (Exception e) {
				throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
			}
			return r;
		};
	}

	@Override
	protected Object[] getInsertParams(Record r) {
		return new Object[]{
			r.getId(),
			r.getChecklistId(),
			r.getAuthorId(),
			r.getCreated(),
			r.getAnswers().toString()
		};
	}

	@Override
	protected Object[] getUpdateParams(Record r) {
		return new Object[]{
			r.getChecklistId(),
			r.getAuthorId(),
			r.getCreated(),
			r.getAnswers().toString(),
			r.getId()
		};
	}

	public List<Record> recordsByChecklist(Integer checklistId) {
		String sql = "SELECT * FROM record WHERE checklist_id = ?";
		return jdbcTemplate.query(sql, getRowMapper(), checklistId);
	}

	public Record insert(Record r) {
		String sql = """
			INSERT INTO record (id, checklist_id, author_id, created, answers)
			VALUES (?, ?, ?, ?, ?::jsonb)
		""";
		return super.insert(r, sql);
	}
}

	