package dev.donaldsonblack.cura.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.donaldsonblack.cura.model.users.User;
import dev.donaldsonblack.cura.model.users.UserCreateRequest;
import dev.donaldsonblack.cura.model.users.UserPatchRequest;
import dev.donaldsonblack.cura.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// READS
	@GetMapping("/users")
	public ResponseEntity<List<User>> list() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> get(@PathVariable Integer id) {
		return userRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/users/by-sub/{sub}")
	public ResponseEntity<User> getBySub(@PathVariable UUID sub) {
		return userRepository.findByCognitoSub(sub)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// CREATE
	@PostMapping("/users")
	public ResponseEntity<User> create(@Valid @RequestBody UserCreateRequest req) {
		User created = userRepository.insert(req.toUserEntity());
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	// PATCH
	@PatchMapping("/users/{id}")
	public ResponseEntity<User> patch(@PathVariable Integer id, @RequestBody UserPatchRequest patch) {
		var existingOpt = userRepository.findById(id);
		if (existingOpt.isEmpty())
			return ResponseEntity.notFound().build();
		var existing = existingOpt.get();
		patch.applyTo(existing);
		boolean ok = userRepository.update(existing);
		return ok ? ResponseEntity.ok(existing) : ResponseEntity.notFound().build();
	}

	// DELETE
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		return userRepository.deleteById(id) ? ResponseEntity.ok().build()
				: ResponseEntity.notFound().build();
	}
}
