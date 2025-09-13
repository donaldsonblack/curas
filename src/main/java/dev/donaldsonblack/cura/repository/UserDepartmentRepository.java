package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.model.UserDepartmentId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepartmentRepository
  extends JpaRepository<UserDepartment, UserDepartmentId> {
  @EntityGraph(attributePaths = { "department", "user" })
  List<UserDepartment> findAllByUserId(Integer userId);

  @EntityGraph(attributePaths = { "user" })
  Page<UserDepartment> findAllByDepartmentId(
    Integer departmentId,
    Pageable pageable
  );
}
