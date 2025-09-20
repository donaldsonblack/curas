package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Department;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Optional<Department> findByName(String name);

	List<Department> findByParentIsNull();

	List<Department> findByParent(Integer parent);

	@Query("select d from Department d " +
			"join UserDepartment ud on ud.department.id = d.id " +
			"where ud.user.sub = :sub")
	Page<Department> findAllForUser(@Param("sub") UUID sub, Pageable pageable);
}
