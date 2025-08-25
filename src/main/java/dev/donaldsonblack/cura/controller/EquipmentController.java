package dev.donaldsonblack.cura.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.donaldsonblack.cura.model.equipment.Equipment;
import dev.donaldsonblack.cura.model.equipment.EquipmentCreateRequest;
// import dev.donaldsonblack.cura.model.equipment.EquipmentDetail;
import dev.donaldsonblack.cura.model.equipment.EquipmentPatchRequest;
import dev.donaldsonblack.cura.repository.EquipmentRepository;

@RestController
@RequestMapping("/api")
public class EquipmentController {

	@Autowired
	private EquipmentRepository equipmentRepository;

	// -------- READS (entity) --------

	@GetMapping("/equipment")
	public ResponseEntity<List<Equipment>> list() {
		return ResponseEntity.ok(equipmentRepository.findAll());
	}

	@GetMapping("/equipment/{id}")
	public ResponseEntity<Equipment> get(@PathVariable Integer id) {
		return equipmentRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// // -------- READS (detail/enriched) --------
	//
	// @GetMapping("/equipment/detail")
	// public ResponseEntity<List<EquipmentDetail>> listDetail() {
	// return ResponseEntity.ok(equipmentRepository.findDetails());
	// }
	//
	// @GetMapping("/equipment/{id}/detail")
	// public ResponseEntity<EquipmentDetail> getDetail(@PathVariable Integer id) {
	// return equipmentRepository.findDetailById(id)
	// .map(ResponseEntity::ok)
	// .orElseGet(() -> ResponseEntity.notFound().build());
	// }

	// -------- CREATE --------

	@PostMapping("/equipment")
	public ResponseEntity<Equipment> create(@Valid @RequestBody EquipmentCreateRequest req) {
		Equipment toInsert = req.toEquipmentEntity();
		Equipment created = equipmentRepository.insert(toInsert);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	// -------- PATCH (partial update) --------

	@PatchMapping("/equipment/{id}")
	public ResponseEntity<?> patch(@PathVariable Integer id, @RequestBody EquipmentPatchRequest patch) {
		var existingOpt = equipmentRepository.findById(id);
		if (existingOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Equipment existing = existingOpt.get();
		patch.applyTo(existing);

		boolean ok = equipmentRepository.update(existing);
		return ok ? ResponseEntity.ok(existing) : ResponseEntity.notFound().build();
	}

	// -------- DELETE --------

	@DeleteMapping("/equipment/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		boolean ok = equipmentRepository.deleteById(id);
		return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
