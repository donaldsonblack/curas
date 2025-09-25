package com.dblck.curas.repository;

import com.dblck.curas.model.UserDepartment;
import com.dblck.curas.model.UserDepartmentId;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepartmentRepository extends JpaRepository<UserDepartment, UserDepartmentId> {
  @EntityGraph(attributePaths = {"department"})
  List<UserDepartment> findAllByUserId(Integer userId);

  // @EntityGraph(attributePaths = { "user" })
  // Page<UserDepartment> findAllByDepartmentId(
  // 		Integer departmentId,
  // 		Pageable pageable);

  @EntityGraph(attributePaths = {"user"})
  List<UserDepartment> findAllByDepartmentId(Integer deptId);
}
