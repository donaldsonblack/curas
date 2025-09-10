package dev.donaldsonblack.cura.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.donaldsonblack.cura.model.Equipment;
import dev.donaldsonblack.cura.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentService {
	
	private final EquipmentRepository repo;

	public Page<Equipment> list(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public Equipment getById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Equipment Not found: " + id));
	}

	public Equipment save(Equipment equipment) {
		return repo.save(equipment);
	}
}
