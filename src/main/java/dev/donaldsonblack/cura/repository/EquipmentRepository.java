package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
	@Query(value = "select e.department.id from Equipment e where e.id=:id", nativeQuery = true)
	Integer findDeptId(Integer id);
}
