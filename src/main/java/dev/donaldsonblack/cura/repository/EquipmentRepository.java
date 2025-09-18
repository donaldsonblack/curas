package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {}
