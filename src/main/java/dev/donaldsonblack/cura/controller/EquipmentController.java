package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.model.Equipment;
import dev.donaldsonblack.cura.service.EquipmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

  @Autowired
  private EquipmentService equipmentService;

  @GetMapping()
  public List<Equipment> getAlLEquipment() {
    // Return the JWT claims as a string
    return equipmentService.findAll();
  }

  @PostMapping()
  public ResponseEntity<String> setEquipment(@RequestBody Equipment e) {
    equipmentService.insert(e);
    return ResponseEntity.ok("Equipment added successfully");
  }
}