package dev.donaldsonblack.cura.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import dev.donaldsonblack.cura.model.Department;

import dev.donaldsonblack.cura.service.DepartmentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService service;

	@GetMapping
	public Page<Department> getAll(Pageable pageable) {
		return service.list(pageable);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Department create(@RequestBody Department department) {
		return service.save(department);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
}
