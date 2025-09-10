package dev.donaldsonblack.cura.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import dev.donaldsonblack.cura.model.Equipment;
import dev.donaldsonblack.cura.service.EquipmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {
	
	private final EquipmentService service;

	@GetMapping
	public Page<Equipment> getAll(Pageable pageable) {
		return service.list(pageable);
	}

	@GetMapping("/{id}")
	public Equipment getById(@PathVariable Integer id) {
		return service.getById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Equipment create(@RequestBody Equipment equipment) {
		return service.save(equipment);
	}
}

