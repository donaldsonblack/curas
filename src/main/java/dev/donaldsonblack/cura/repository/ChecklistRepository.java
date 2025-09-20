package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Checklist;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

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

	// @Query(value = "select c.department_id from Checklist c where c.id=:id")
	// Integer findDeptId(Integer id);

	@Query(value = "select department_id from checklists where id = :id", nativeQuery = true)
	Integer findDeptId(@Param("id") Integer id);

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
			@Param("q") String query, @Param("deptId") Integer departmentId, Pageable pageable);

}
