package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.model.UserDepartmentId;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDepartmentRepository extends JpaRepository<UserDepartment, UserDepartmentId> {
	@EntityGraph(attributePaths = { "department" })
	List<UserDepartment> findAllByUserId(Integer userId);

	List<UserDepartment> findAllByUserSub(UUID sub);

	// @EntityGraph(attributePaths = { "user" })
	// Page<UserDepartment> findAllByDepartmentId(
	// Integer departmentId,
	// Pageable pageable);

	@EntityGraph(attributePaths = { "user" })
	List<UserDepartment> findAllByDepartmentId(Integer deptId);

	@Query("select (count(ud) > 0) " +
			"from UserDepartment ud " +
			"where ud.user.sub = :sub and ud.department.id = :deptId")
	boolean isMember(@Param("sub") String sub, @Param("deptId") Integer deptId);

	@Query("select (count(ud) > 0) " +
			"from UserDepartment ud " +
			"where ud.user.sub = :sub and ud.department.id = :deptId " +
			"and (ud.role = dev.donaldsonblack.cura.model.Role.MANAGER " +
			"     or ud.role = dev.donaldsonblack.cura.model.Role.ADMIN)")
	boolean isManager(@Param("sub") String sub, @Param("deptId") Integer deptId);
}
