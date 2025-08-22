package dev.donaldsonblack.cura.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dev.donaldsonblack.cura.model.Equipment;

@Repository
public class EquipmentRepository extends JdbcGenericRepository<Equipment, Integer> {

  public EquipmentRepository(JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

  @Override
  protected String getTableName() {
    return "equipment";
  }

  @Override
  protected String getIdColumn() {
    return "id";
  }

  @Override
  protected RowMapper<Equipment> getRowMapper() {
    return (rs, row) -> {
      Equipment e = new Equipment();
      e.setId(rs.getInt("id"));
      e.setDepartmentId(rs.getInt("department_id"));
      e.setSerial(rs.getString("serial"));
      e.setModel(rs.getString("model"));
      e.setName(rs.getString("name"));
      return e;
    };
  }

  @Override
  protected Object[] getInsertParams(Equipment e) {
    return new Object[]{
      e.getId(),
      e.getDepartmentId(),
      e.getSerial(),
      e.getModel(),
      e.getName()
    };
  }

  @Override
  protected Object[] getUpdateParams(Equipment e) {
    return new Object[]{
      e.getDepartmentId(),
      e.getSerial(),
      e.getModel(),
      e.getName(),
      e.getId()
    };
  }
}
