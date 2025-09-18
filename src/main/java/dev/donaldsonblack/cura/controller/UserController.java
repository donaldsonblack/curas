package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.config.JsonViews;
import dev.donaldsonblack.cura.dto.user.UserCreateRequest;
import dev.donaldsonblack.cura.dto.user.UserUpdateRequest;
import dev.donaldsonblack.cura.model.User;
import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.service.UserDepartmentService;
import dev.donaldsonblack.cura.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	private final UserDepartmentService userDepartmentService;

	@GetMapping
	@Operation(summary = "Get page of all users")
	public ResponseEntity<Page<User>> getAll(Pageable pageable) {
		Page<User> page = service.list(pageable);

		if (page.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(page);
	}

	@GetMapping("/{userId}/department")
	@Operation(summary = "Gets list of departments a user is a member of")
	@JsonView(JsonViews.departmentMinimal.class)
	public ResponseEntity<List<UserDepartment>> listUserDepartments(@PathVariable Integer userId) {
		List<UserDepartment> list = userDepartmentService.listMembershipForUser(userId);

		if (list.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by id")
	public ResponseEntity<User> getById(@PathVariable Integer id) {
		return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Create user in database")
	public ResponseEntity<User> create(@Valid @RequestBody UserCreateRequest req) {
		User created = service.save(req);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(created.getId())
				.toUri();

		return ResponseEntity.created(location).body(created);
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Patch a User entity")
	public ResponseEntity<User> update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest req) {
		return service.updateUser(id, req).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	@Operation(summary = "Put User, patches existing completely or if not found creates user")
	public ResponseEntity<User> put(@PathVariable Integer id, @Valid @RequestBody UserCreateRequest req) {
		Optional<User> put = service.putUser(id, req);

		if (put.isEmpty()) {
			return create(req);
		} 

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes entity from database")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
