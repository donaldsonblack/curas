package com.dblck.curas.controller;

import com.dblck.curas.dto.checklist.ChecklistCreateRequest;
import com.dblck.curas.dto.checklist.ChecklistTableView;
import com.dblck.curas.dto.checklist.ChecklistUpdateRequest;
import com.dblck.curas.model.Checklist;
import com.dblck.curas.service.ChecklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/checklists")
@RequiredArgsConstructor
public class ChecklistController {

  private final ChecklistService service;

  @Operation(
      summary = "Retrieve all checklists",
      description = "Returns a paginated list of all checklists.",
      tags = {"Checklist"})
  @GetMapping
  public ResponseEntity<Page<Checklist>> getAll(Pageable pageable) {
    Page<Checklist> page = service.list(pageable);

    if (page.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(page);
  }

  @Operation(
      summary = "Retrive all checklists for human readable",
      description = "Fetches all checklists, but replaces Ids with names for view in ui",
      tags = {"Checklist"})
  @GetMapping("/table")
  public ResponseEntity<Page<ChecklistTableView>> getTableView(Pageable pageable) {
    Page<ChecklistTableView> page = service.tableView(pageable);

    if (page.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(page);
  }

  @Operation(
      summary = "Get a checklist by ID",
      description = "Fetches a single checklist by its unique identifier.",
      tags = {"Checklist"})
  @GetMapping("/{id}")
  public ResponseEntity<Checklist> getById(Integer id) {
    return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Create a new checklist",
      description = "Creates and returns a new checklist.",
      tags = {"Checklist"})
  @PostMapping
  public ResponseEntity<Checklist> create(@Valid @RequestBody ChecklistCreateRequest req) {
    Checklist created = service.save(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

    return ResponseEntity.created(location).body(created);
  }

  @Operation(
      summary = "Delete a checklist",
      description = "Deletes a checklist by its unique identifier.",
      tags = {"Checklist"})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Partially update a checklist",
      description = "Applies partial updates to an existing checklist.",
      tags = {"Checklist"})
  @PatchMapping("/{id}")
  public ResponseEntity<Checklist> patch(
      @PathVariable Integer id, @Valid @RequestBody ChecklistUpdateRequest req) {
    return service.patch(id, req).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Replace or create a checklist",
      description = "Replaces a checklist if it exists, or creates a new one if not.",
      tags = {"Checklist"})
  @PutMapping("/{id}")
  public ResponseEntity<Checklist> put(Integer id, ChecklistCreateRequest req) {
    Optional<Checklist> put = service.put(id, req);

    if (put.isEmpty()) {
      return create(req);
    }

    return ResponseEntity.ok().build();
  }
}
