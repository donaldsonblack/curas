package dev.donaldsonblack.cura.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.donaldsonblack.cura.config.JsonViews;
import dev.donaldsonblack.cura.dto.department.DepartmentCreateRequest;
import dev.donaldsonblack.cura.dto.department.DepartmentUpdateRequest;
import dev.donaldsonblack.cura.model.Department;
import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.service.DepartmentService;
import dev.donaldsonblack.cura.service.UserDepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
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
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

  private final DepartmentService service;
  private final UserDepartmentService userDepartmentService;

  @Operation(
      summary = "List all departments",
      description = "Returns a paginated list of all departments.",
      tags = {"Department"})
  @GetMapping
  public ResponseEntity<Page<Department>> getAll(Pageable pageable) {
    Page<Department> page = service.list(pageable);

    if (page.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(page);
  }

  @Operation(
      summary = "Get department by ID",
      description = "Fetch a single department by its ID.",
      tags = {"Department"})
  @GetMapping("/{id}")
  public ResponseEntity<Department> getById(@PathVariable Integer id) {
    return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Create new department",
      description = "Creates a new department from the provided request.",
      tags = {"Department"})
  @PostMapping
  public ResponseEntity<Department> create(@Valid @RequestBody DepartmentCreateRequest req) {
    Department created = service.save(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

    return ResponseEntity.created(location).body(created);
  }

  @Operation(
      summary = "Delete department",
      description = "Deletes a department by ID.",
      tags = {"Department"})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "List department members",
      description = "Returns the list of users assigned to a department.",
      tags = {"Department"})
  @GetMapping("/{deptId}/members")
  @JsonView(JsonViews.userMinimal.class)
  public ResponseEntity<List<UserDepartment>> listDepartmentMembers(@PathVariable Integer deptId) {
    List<UserDepartment> list = userDepartmentService.membershipsForDepartment(deptId);

    if (list.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(list);
  }

  // TODO Patch and Put

  @Operation(
      summary = "Update department (partial)",
      description = "Partially update fields of a department by ID.",
      tags = {"Department"})
  @PatchMapping("/{id}")
  public ResponseEntity<Department> put(
      @PathVariable Integer id, @Valid @RequestBody DepartmentUpdateRequest req) {
    return service
        .update(id, req)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Replace department",
      description = "Replaces an existing department or creates a new one if it doesn't exist.",
      tags = {"Department"})
  @PutMapping("/{id}")
  public ResponseEntity<Department> patch(
      @PathVariable Integer id, @Valid @RequestBody DepartmentCreateRequest req) {
    Optional<Department> put = service.put(id, req);

    if (put.isEmpty()) {
      return create(req);
    }

    return ResponseEntity.ok().build();
  }
}
