package dev.donaldsonblack.cura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.donaldsonblack.cura.repository.RecordRepository;
import dev.donaldsonblack.cura.model.Record;

@RestController
@RequestMapping("/api")
public class RecordController {

	@Autowired
	private RecordRepository recordRepository;

	@GetMapping("/record")
	public List<Record> getAllRecord() {
		return recordRepository.findAll();
	}

	@GetMapping("/record/{id}")
	public ResponseEntity<Record> getById(@PathVariable Integer id) {
		return recordRepository.findById(id)
			.map(ResponseEntity::ok)
			.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/checklists/{id}/records")
	public List<Record> recordsByChecklist(@PathVariable Integer id) {
		return recordRepository.recordsByChecklist(id);
	}

	@PostMapping("/record")
	public ResponseEntity<String> setRecord(@RequestBody Record r) {
		recordRepository.insert(r);
		return ResponseEntity.ok("Record entered successfully");
	}

	@DeleteMapping("/record/{id}")
	public ResponseEntity<?> deleteRecord(@PathVariable Integer id) {
		boolean success = recordRepository.deleteById(id);
		return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
