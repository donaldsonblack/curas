package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.model.Checklist;
import dev.donaldsonblack.cura.repository.ChecklistRepository;
import dev.donaldsonblack.cura.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

  private final ChecklistService service;

  // Full entity pagination (includes questions JSON)
  @GetMapping
  public Page<Checklist> getAll(Pageable pageable) {
    return service.list(pageable);
  }

  // Lightweight projection for list screens (excludes questions JSON)
  @GetMapping("/list")
  public Page<ChecklistRepository.ChecklistListView> getList(
    @RequestParam(required = false) Integer departmentId,
    Pageable pageable
  ) {
    return service.listLight(departmentId, pageable);
  }

  // Search by text (name, description, type)
  @GetMapping("/search")
  public Page<ChecklistRepository.ChecklistListView> search(
    @RequestParam String q,
    @RequestParam(required = false) Integer departmentId,
    Pageable pageable
  ) {
    return service.search(q, departmentId, pageable);
  }

  // Get single checklist by ID
  @GetMapping("/{id}")
  public Checklist getById(@PathVariable Integer id) {
    return service.getById(id);
  }

  // Create/update checklist
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Checklist create(@RequestBody Checklist checklist) {
    return service.save(checklist);
  }

  // Delete checklist
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    service.delete(id);
  }
}
