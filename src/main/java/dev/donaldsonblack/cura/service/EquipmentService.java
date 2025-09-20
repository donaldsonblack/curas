package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.dto.equipment.EquipmentCreateRequest;
import dev.donaldsonblack.cura.dto.equipment.EquipmentUpdateRequest;
import dev.donaldsonblack.cura.model.Equipment;
import dev.donaldsonblack.cura.repository.EquipmentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {

  private final EquipmentRepository repo;

  public Page<Equipment> list(Pageable pageable) {
    return repo.findAll(pageable);
  }

  // public Equipment getById(Integer id) {
  // return repo.findById(id).orElseThrow(() -> new
  // IllegalArgumentException("Equipment Not found: " + id));
  // }

  public Optional<Equipment> getById(Integer id) {
    return repo.findById(id);
  }

  public Equipment save(Equipment equipment) {
    return repo.save(equipment);
  }

  public Equipment save(EquipmentCreateRequest req) {
    Equipment eq =
        Equipment.builder()
            .departmentId(req.deptId())
            .serial(req.serial())
            .model(req.model())
            .name(req.name())
            .build();

    return repo.save(eq);
  }

  public void delete(Integer id) {
    repo.deleteById(id);
  }

  public Optional<Equipment> update(Integer id, EquipmentUpdateRequest req) {
    return repo.findById(id)
        .map(
            eq -> {
              req.name().ifPresent(eq::setName);
              req.serial().ifPresent(eq::setSerial);
              req.model().ifPresent(eq::setModel);
              req.deptId().ifPresent(eq::setDepartmentId);

              return repo.save(eq);
            });
  }

  public Optional<Equipment> put(Integer id, EquipmentCreateRequest req) {
    return repo.findById(id)
        .map(
            eq -> {
              eq.setName(req.name());
              eq.setSerial(req.serial());
              eq.setModel(req.model());
              eq.setDepartmentId(req.deptId());

              return repo.save(eq);
            });
  }
}
