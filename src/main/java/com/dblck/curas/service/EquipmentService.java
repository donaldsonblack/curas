package com.dblck.curas.service;

import com.dblck.curas.dto.equipment.EquipmentCreateRequest;
import com.dblck.curas.dto.equipment.EquipmentUpdateRequest;
import com.dblck.curas.model.Equipment;
import com.dblck.curas.repository.EquipmentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {

  private final EquipmentRepository repo;
  private static final String EQUIPMENT_CACHE = "equipmentById";

  public Page<Equipment> list(Pageable pageable) {
    return repo.findAll(pageable);
  }

  @Cacheable(value = EQUIPMENT_CACHE, key = "#p0")
  public Optional<Equipment> getById(Integer id) {
    return repo.findById(id);
  }

  @CachePut(value = EQUIPMENT_CACHE, key = "#result.id", unless = "#result == null")
  public Equipment save(Equipment equipment) {
    return repo.save(equipment);
  }

  @CachePut(value = EQUIPMENT_CACHE, key = "#result.id", unless = "#result == null")
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

  @CacheEvict(value = EQUIPMENT_CACHE, key = "#p0")
  public void delete(Integer id) {
    repo.deleteById(id);
  }

  @CacheEvict(value = EQUIPMENT_CACHE, key = "#p0")
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

  @CacheEvict(value = EQUIPMENT_CACHE, key = "#p0")
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
