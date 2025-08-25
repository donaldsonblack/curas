package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.model.records.Record;
import dev.donaldsonblack.cura.model.records.RecordDetail;
import dev.donaldsonblack.cura.model.records.RecordPatchRequest;
import dev.donaldsonblack.cura.model.records.RecordCreateRequest;
import dev.donaldsonblack.cura.repository.RecordRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/records")
@Validated
public class RecordController {

    private final RecordRepository repo;

    public RecordController(RecordRepository repo) {
        this.repo = repo;
    }

    // -------- Create --------
    @PostMapping
    public ResponseEntity<Record> create(@Valid @RequestBody RecordCreateRequest body) {
        Record created = repo.create(body);
        return ResponseEntity
                .created(URI.create("/api/records/" + created.getId()))
                .body(created);
    }

    // -------- Read (entity) --------
    @GetMapping
    public List<Record> list(
            @RequestParam(required = false) Integer checklistId,
            @RequestParam(required = false) Integer authorId
    ) {
        if (checklistId != null) {
            return repo.findRecordsByChecklistId(checklistId);
        } else if (authorId != null) {
            return repo.findRecordsByAuthorId(authorId);
        }
        return repo.findAllRecords();
    }

    @GetMapping("/{id}")
    public Record get(@PathVariable int id) {
        return repo.findRecordById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + " not found"));
    }

    // -------- Read (detail / joined) --------
    @GetMapping("/details")
    public List<RecordDetail> listDetails(
            @RequestParam(required = false) Integer checklistId,
            @RequestParam(required = false) Integer authorId
    ) {
        if (checklistId != null) {
            return repo.findDetailsByChecklistId(checklistId);
        } else if (authorId != null) {
            return repo.findDetailsByAuthorId(authorId);
        }
        return repo.findAllDetails();
    }

    @GetMapping("/details/{id}")
    public RecordDetail getDetail(@PathVariable int id) {
        return repo.findDetailById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + " not found"));
    }

    // -------- Patch (partial update) --------
    @PatchMapping("/{id}")
    public Record patch(@PathVariable int id, @Valid @RequestBody RecordPatchRequest body) {
        return repo.patch(id, body)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + " not found"));
    }

    // -------- Delete --------
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        boolean removed = repo.deleteById(id);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + " not found");
        }
    }
}
