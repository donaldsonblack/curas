package dev.donaldsonblack.cura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.donaldsonblack.cura.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
}
