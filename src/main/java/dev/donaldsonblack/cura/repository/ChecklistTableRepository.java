package dev.donaldsonblack.cura.repository;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.donaldsonblack.cura.model.ChecklistTable;

@Repository
public class ChecklistTableRepository {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final JdbcTemplate jdbcTemplate;

	public ChecklistTableRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	protected RowMapper<ChecklistTable> getRowMapper() {
		return (rs, rowNum) -> {
			ChecklistTable t = new ChecklistTable();
			t.setName(rs.getString("checklist_name"));
			t.setDescription(rs.getString("checklist_description"));
			t.setEquipmentName(rs.getString("equip_name"));
			t.setEquipmentModel(rs.getString("equip_model"));
			t.setDepartmentName(rs.getString("department_name"));
			t.setFrequency(rs.getString("frequency"));
			t.setAuthorName(rs.getString("author"));
			t.setCreatedDate(new Date(rs.getTimestamp("created").getTime()));
			try {
				t.setQuestions(objectMapper.readTree(rs.getString("questions")));
			} catch (Exception e) {
				throw new RuntimeException("Invalid JSON for checklist table questions", e);
			}
			return t;
		};
	}

	public List<ChecklistTable> findAll() {

		// sql = "select e.name AS equip_name, e.model as equip_model, d.name as
		// department_name, c.type as frequency, u.name as author, c.created as created,
		// c.questions as questions from checklists c join equipment e on c.equipment_id
		// = e.id join users u on c.author_id = u.id join department d on
		// c.department_id = d.id;";

		String sql = """
				SELECT
						e.name AS equip_name,
						e.model AS equip_model,
						d.name AS department_name,
						c.type AS frequency,
						u.name AS author,
						c.created AS created,
						c.questions AS questions,
						c.name AS checklist_name,
						c.description AS checklist_description
				FROM checklists c
				JOIN equipment e ON c.equipment_id = e.id
				JOIN users u ON c.author_id = u.id
				JOIN department d ON c.department_id = d.id;
				""";

		return jdbcTemplate.query(sql, getRowMapper());
	}
}
