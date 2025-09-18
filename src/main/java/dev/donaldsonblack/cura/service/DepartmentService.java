package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.dto.department.DepartmentCreateRequest;
import dev.donaldsonblack.cura.dto.department.DepartmentUpdateRequest;
import dev.donaldsonblack.cura.model.Department;
import dev.donaldsonblack.cura.repository.DepartmentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  private final DepartmentRepository repo;

  public Page<Department> list(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public Optional<Department> getById(Integer id) {
    return repo.findById(id);
  }

  public void delete(Integer id) {
    repo.deleteById(id);
  }

  public Department save(DepartmentCreateRequest req) {
    Department d =
        Department.builder().name(req.name()).location(req.location()).parent(req.parent()).build();

    return repo.save(d);
  }

  public Optional<Department> update(Integer id, DepartmentUpdateRequest req) {
    return repo.findById(id)
        .map(
            dep -> {
              req.name().ifPresent(dep::setName);
              req.location().ifPresent(dep::setLocation);
              req.parent().ifPresent(dep::setParent);

              return repo.save(dep);
            });
  }

  public Optional<Department> put(Integer id, DepartmentCreateRequest req) {
    return repo.findById(id)
        .map(
            dep -> {
              dep.setName(req.name());
              dep.setLocation(req.location());
              dep.setParent(req.parent());

              return repo.save(dep);
            });
  }
}
