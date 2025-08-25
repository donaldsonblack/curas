package dev.donaldsonblack.cura.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.donaldsonblack.cura.model.checklists.Checklist;
import dev.donaldsonblack.cura.model.checklists.ChecklistCreateRequest;
import dev.donaldsonblack.cura.model.checklists.ChecklistPatchRequest;
import dev.donaldsonblack.cura.model.checklists.ChecklistDetail;
import dev.donaldsonblack.cura.repository.ChecklistRepository;

@RestController
@RequestMapping("/api")
public class ChecklistController {

	@Autowired
	private ChecklistRepository checklistRepository;

	@GetMapping("/checklists/{id}/detail")
	public ResponseEntity<ChecklistDetail> getDetail(@PathVariable Integer id) {
		return checklistRepository.findDetailById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/checklists/detail")
	public ResponseEntity<List<ChecklistDetail>> listDetail(
			@RequestParam(required = false) Integer departmentId,
			@RequestParam(required = false) Integer equipmentId) {
		var rows = checklistRepository.findDetails();
		return ResponseEntity.ok(rows);
	}

	@GetMapping("/checklists")
	public ResponseEntity<List<Checklist>> getAll() {
		List<Checklist> checklists = checklistRepository.findAll();
		return ResponseEntity.ok(checklists);
	}

	@GetMapping("/checklists/{id}")
	public ResponseEntity<Checklist> getById(@PathVariable Integer id) {
		return checklistRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/checklists/{id}/questions")
	public ResponseEntity<String> getQuestionsById(@PathVariable Integer id) {
		return checklistRepository.findById(id)
				.map(c -> ResponseEntity.ok(c.getQuestions().toString()))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/equipment/{id}/checklists")
	public ResponseEntity<List<Checklist>> getAllByEquipmentId(@PathVariable Integer id) {
		List<Checklist> checklists = checklistRepository.getByEquipmentId(id);
		return ResponseEntity.ok(checklists);
	}

	@PostMapping("/checklists")
	public ResponseEntity<Checklist> create(@Valid @RequestBody ChecklistCreateRequest req) {
		Checklist toInsert = req.toChecklistEntity();
		Checklist created = checklistRepository.insert(toInsert);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	// @PutMapping("/checklists/{id}")
	// public ResponseEntity<?> updateChecklist(@PathVariable Integer id, @Valid
	// @RequestBody ChecklistUpdateRequest req) {
	// if (!id.equals(req.getId())) {
	// return ResponseEntity.badRequest().body("Path id and body id must match");
	// }
	// var existing = checklistRepository.findById(id);
	// if (existing.isEmpty())
	// return ResponseEntity.notFound().build();
	// boolean success = checklistRepository.update(req.toChecklistEntity());
	// return success ? ResponseEntity.ok().build() :
	// ResponseEntity.notFound().build();
	// }

	@PatchMapping("/checklists/{id}")
	public ResponseEntity<?> patchChecklist(@PathVariable Integer id, @RequestBody ChecklistPatchRequest patch) {
		var existingOpt = checklistRepository.findById(id);
		if (existingOpt.isEmpty())
			return ResponseEntity.notFound().build();
		var existing = existingOpt.get();
		patch.applyTo(existing);
		boolean success = checklistRepository.update(existing);
		return success ? ResponseEntity.ok(existing) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/checklists/{id}")
	public ResponseEntity<?> deleteChecklist(@PathVariable Integer id) {
		boolean success = checklistRepository.deleteById(id);
		return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
