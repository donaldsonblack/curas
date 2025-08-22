package dev.donaldsonblack.cura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.donaldsonblack.cura.repository.ChecklistTableRepository;
import dev.donaldsonblack.cura.model.ChecklistTable;

@RestController
@RequestMapping("/api")
public class ChecklistTableController {

	@Autowired
	private ChecklistTableRepository checklistTableRepository;

	@GetMapping("/checklists/table")
	public ResponseEntity<List<ChecklistTable>> getAll() {
		List<ChecklistTable> lists = checklistTableRepository.findAll();
		return ResponseEntity.ok(lists);
	}
}
