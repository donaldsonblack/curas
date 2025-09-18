package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.dto.equipment.EquipmentCreateRequest;
import dev.donaldsonblack.cura.dto.equipment.EquipmentUpdateRequest;
import dev.donaldsonblack.cura.model.Equipment;
import dev.donaldsonblack.cura.service.EquipmentService;
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
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

  private final EquipmentService service;

  @Operation(
      summary = "List all equipment (paginated)",
      description = "Retrieve a paginated list of all equipment records in the system",
      tags = {"Equipment"})
  @GetMapping
  public ResponseEntity<Page<Equipment>> getAll(Pageable pageable) {
    Page<Equipment> page = service.list(pageable);

    if (page.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(page);
  }

  @Operation(
      summary = "Get equipment by id",
      description = "Fetch a single equipment record by its unique identifier",
      tags = {"Equipment"})
  @GetMapping("/{id}")
  public ResponseEntity<Equipment> getById(@PathVariable Integer id) {
    return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Create new equipment",
      description = "Create and persist a new equipment record",
      tags = {"Equipment"})
  @PostMapping
  public ResponseEntity<Equipment> create(@Valid @RequestBody EquipmentCreateRequest req) {
    Equipment created = service.save(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

    return ResponseEntity.created(location).body(created);
  }

  @Operation(
      summary = "Partially update equipment",
      description = "Partially update fields of an existing equipment record",
      tags = {"Equipment"})
  @PatchMapping("/{id}")
  public ResponseEntity<Equipment> update(
      @PathVariable Integer id, @Valid @RequestBody EquipmentUpdateRequest req) {
    return service
        .update(id, req)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Replace or create equipment",
      description = "Replace an existing equipment record or create a new one if it does not exist",
      tags = {"Equipment"})
  @PutMapping("/{id}")
  public ResponseEntity<Equipment> put(
      @PathVariable Integer id, @Valid @RequestBody EquipmentCreateRequest req) {
    Optional<Equipment> eq = service.put(id, req);

    if (eq.isEmpty()) {
      return create(req);
    }

    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "Delete equipment by id",
      description = "Delete an equipment record by its unique identifier",
      tags = {"Equipment"})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
