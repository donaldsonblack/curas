package dev.donaldsonblack.cura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.donaldsonblack.cura.model.Checklist;
import dev.donaldsonblack.cura.repository.ChecklistRepository;

@RestController
@RequestMapping("/api")
public class ChecklistController {

	@Autowired
	private ChecklistRepository checklistRepository;

	@GetMapping("/checklists")
	public ResponseEntity<List<Checklist>> getAll() {
		List<Checklist> checklists = checklistRepository.findAll();
		return ResponseEntity.ok(checklists);
	}

	@PostMapping("/checklists")
	public ResponseEntity<Checklist> create(@RequestBody Checklist req) {
		Checklist created = checklistRepository.insert(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
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

	@PutMapping("/checklists/{id}")
	public ResponseEntity<?> updateChecklist(@PathVariable Integer id, @RequestBody Checklist checklist) {
		checklist.setId(id);
		boolean success = checklistRepository.update(checklist);
		return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/checklists/{id}")
	public ResponseEntity<?> deleteChecklist(@PathVariable Integer id) {
		boolean success = checklistRepository.deleteById(id);
		return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}

	// // Getes all the information the user will need to create a new checklist, e.g.
	// // list of all equipment, departments etc.
	// @GetMapping("/checklists/form-data")
	// public ResponseEntity<?> getCreateChecklistInformation() {

	// }

}
