package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

	Page<Checklist> findByDepartmentId(Integer departmentId, Pageable pageable);

	Page<Checklist> findByType(String type, Pageable pageable);

	interface ChecklistListView {
		Integer getId();

		String getName();

		String getDescription();

		String getType();

		Instant getCreated();

		Integer getDepartmentId();

		Integer getEquipmentId();

		Integer getAuthorId();
	}

	Page<ChecklistListView> findAllByDepartmentId(Integer departmentId, Pageable pageable);

	@Query(value = """
			select c.id, c.name, c.description, c.type, c.created,
			       c.department_id as departmentId,
			       c.equipment_id  as equipmentId,
			       c.author_id     as authorId
			from checklists c
			where (:deptId is null or c.department_id = :deptId)
			  and (
			       c.name        ilike concat('%', :q, '%')
			    or c.description ilike concat('%', :q, '%')
			    or c.type        ilike concat('%', :q, '%')
			  )
			""", countQuery = """
			select count(*)
			from checklists c
			where (:deptId is null or c.department_id = :deptId)
			  and (
			       c.name        ilike concat('%', :q, '%')
			    or c.description ilike concat('%', :q, '%')
			    or c.type        ilike concat('%', :q, '%')
			  )
			""", nativeQuery = true)
	Page<ChecklistListView> searchListView(
			@Param("q") String query,
			@Param("deptId") Integer departmentId,
			Pageable pageable);
}
