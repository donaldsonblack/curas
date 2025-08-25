package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.equipment.Equipment;
// import dev.donaldsonblack.cura.model.equipment.EquipmentDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class EquipmentRepository {

	private final NamedParameterJdbcTemplate jdbc;

	@Autowired
	public EquipmentRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	// ---------- RowMapper for Equipment (entity) ----------
	private static final RowMapper<Equipment> EQUIPMENT_MAPPER = new RowMapper<>() {
		@Override
		public Equipment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Equipment e = new Equipment();
			e.setId(rs.getInt("id"));
			if (rs.wasNull())
				e.setId(null);
			e.setDepartmentId(rs.getInt("department_id"));
			if (rs.wasNull())
				e.setDepartmentId(null);
			e.setSerial(rs.getString("serial"));
			e.setModel(rs.getString("model"));
			e.setName(rs.getString("name"));
			return e;
		}
	};

	// ---------- CRUD ----------

	public List<Equipment> findAll() {
		String sql = "SELECT id, department_id, serial, model, name FROM equipment ORDER BY id DESC";
		return jdbc.query(sql, EQUIPMENT_MAPPER);
	}

	public Optional<Equipment> findById(Integer id) {
		String sql = "SELECT id, department_id, serial, model, name FROM equipment WHERE id = :id";
		try {
			return Optional.ofNullable(
					jdbc.queryForObject(sql, Map.of("id", id), EQUIPMENT_MAPPER));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Equipment insert(Equipment e) {
		String sql = """
				INSERT INTO equipment (department_id, serial, model, name)
				VALUES (:departmentId, :serial, :model, :name)
				RETURNING id, department_id, serial, model, name
				""";

		Map<String, Object> params = new HashMap<>();
		params.put("departmentId", e.getDepartmentId());
		params.put("serial", e.getSerial());
		params.put("model", e.getModel());
		params.put("name", e.getName());

		return jdbc.queryForObject(sql, params, EQUIPMENT_MAPPER);
	}

	public boolean update(Equipment e) {
		String sql = """
				UPDATE equipment
				   SET department_id = :departmentId,
				       serial        = :serial,
				       model         = :model,
				       name          = :name
				 WHERE id = :id
				""";

		Map<String, Object> params = new HashMap<>();
		params.put("id", e.getId());
		params.put("departmentId", e.getDepartmentId());
		params.put("serial", e.getSerial());
		params.put("model", e.getModel());
		params.put("name", e.getName());

		return jdbc.update(sql, params) > 0;
	}

	public boolean deleteById(Integer id) {
		String sql = "DELETE FROM equipment WHERE id = :id";
		return jdbc.update(sql, Map.of("id", id)) > 0;
	}

	// ---------- Enriched "detail" queries ----------

	// public List<EquipmentDetail> findDetails() {
	// String sql = """
	// SELECT
	// e.id,
	// e.department_id,
	// d.name AS department_name,
	// e.serial,
	// e.model,
	// e.name
	// FROM equipment e
	// JOIN department d ON d.id = e.department_id
	// ORDER BY e.id DESC
	// """;
	//
	// return jdbc.query(sql, (rs, n) -> new EquipmentDetail.Builder()
	// .id(rs.getInt("id"))
	// .departmentId(rs.getInt("department_id"))
	// .departmentName(rs.getString("department_name"))
	// .serial(rs.getString("serial"))
	// .model(rs.getString("model"))
	// .name(rs.getString("name"))
	// .build());
	// }
	//
	// public Optional<EquipmentDetail> findDetailById(Integer id) {
	// String sql = """
	// SELECT
	// e.id,
	// e.department_id,
	// d.name AS department_name,
	// e.serial,
	// e.model,
	// e.name
	// FROM equipment e
	// JOIN department d ON d.id = e.department_id
	// WHERE e.id = :id
	// """;
	//
	// try {
	// return Optional.ofNullable(
	// jdbc.queryForObject(sql, Map.of("id", id), (rs, n) -> new
	// EquipmentDetail.Builder()
	// .id(rs.getInt("id"))
	// .departmentId(rs.getInt("department_id"))
	// .departmentName(rs.getString("department_name"))
	// .serial(rs.getString("serial"))
	// .model(rs.getString("model"))
	// .name(rs.getString("name"))
	// .build()));
	// } catch (EmptyResultDataAccessException ex) {
	// return Optional.empty();
	// }
	// }

	// ---------- Optional helpers ----------

	public List<Equipment> findByDepartmentId(Integer departmentId) {
		String sql = """
				SELECT id, department_id, serial, model, name
				FROM equipment
				WHERE department_id = :departmentId
				ORDER BY id DESC
				""";
		return jdbc.query(sql, Map.of("departmentId", departmentId), EQUIPMENT_MAPPER);
	}
}
