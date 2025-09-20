package dev.donaldsonblack.cura.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.donaldsonblack.cura.config.JsonViews;
import dev.donaldsonblack.cura.dto.user.UserCreateRequest;
import dev.donaldsonblack.cura.dto.user.UserUpdateRequest;
import dev.donaldsonblack.cura.model.User;
import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.service.UserDepartmentService;
import dev.donaldsonblack.cura.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;
  private final UserDepartmentService userDepartmentService;

  @Operation(
      summary = "List all users (paginated)",
      description = "Retrieve a paginated list of all users in the system",
      tags = {"User"})
  @GetMapping
  public ResponseEntity<Page<User>> getAll(Pageable pageable) {
    Page<User> page = service.list(pageable);

    if (page.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(page);
  }

  @Operation(
      summary = "List departments for a user",
      description = "Retrieve all department memberships for a given user",
      tags = {"User"})
  @GetMapping("/{userId}/department")
  @JsonView(JsonViews.departmentMinimal.class)
  public ResponseEntity<List<UserDepartment>> listUserDepartments(@PathVariable Integer userId) {
    List<UserDepartment> list = userDepartmentService.listMembershipForUser(userId);

    if (list.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(list);
  }

  @Operation(
      summary = "Get a user by id",
      description = "Fetch a single user by their unique identifier",
      tags = {"User"})
  @GetMapping("/{id}")
  public ResponseEntity<User> getById(@PathVariable Integer id) {
    return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Create a new user",
      description = "Create and persist a new user in the system",
      tags = {"User"})
  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody UserCreateRequest req) {
    User created = service.save(req);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

    return ResponseEntity.created(location).body(created);
  }

  @Operation(
      summary = "Partially update a user",
      description = "Partially update the fields of an existing user",
      tags = {"User"})
  @PatchMapping("/{id}")
  public ResponseEntity<User> update(
      @PathVariable Integer id, @Valid @RequestBody UserUpdateRequest req) {
    return service
        .updateUser(id, req)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Replace or create a user",
      description = "Replace an existing user or create a new one if it does not exist",
      tags = {"User"})
  @PutMapping("/{id}")
  public ResponseEntity<User> put(
      @PathVariable Integer id, @Valid @RequestBody UserCreateRequest req) {
    Optional<User> put = service.putUser(id, req);

    if (put.isEmpty()) {
      return create(req);
    }

    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "Delete a user",
      description = "Delete a user by their unique identifier",
      tags = {"User"})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
