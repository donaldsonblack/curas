package dev.donaldsonblack.cura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.donaldsonblack.cura.model.Equipment;
import dev.donaldsonblack.cura.repository.EquipmentRepository;

@Service
public class EquipmentService {

  private final EquipmentRepository equipmentRepository;

  public EquipmentService(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  public void insert(Equipment c) {
    
  }

  public List<Equipment> findAll() {
    return equipmentRepository.findAll();
  }
}
