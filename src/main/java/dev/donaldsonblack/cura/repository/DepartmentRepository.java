package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
  Optional<Department> findByName(String name);

  List<Department> findByParentIsNull();

  List<Department> findByParent(Integer parent);
}
