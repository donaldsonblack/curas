package dev.donaldsonblack.cura.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import dev.donaldsonblack.cura.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import dev.donaldsonblack.cura.model.Department;

@Service
@RequiredArgsConstructor
public class DepartmentService {

	private final DepartmentRepository repo;

	public Page<Department> list(Pageable pageable) {
		return repo.findAll(pageable);
	} 

	public Department getById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Department not found: " + id));
	}

	public Department save(Department department) {
		return repo.save(department);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
}

