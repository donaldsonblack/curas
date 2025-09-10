package dev.donaldsonblack.cura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.donaldsonblack.cura.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Optional<Department> findByName(String name);

	List<Department> findByParentIsNull();

	List<Department> findByParentId(Long parentId);
}
