package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.model.Checklist;
import dev.donaldsonblack.cura.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistService {

	private final ChecklistRepository repo;

	public Page<Checklist> list(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public Page<ChecklistRepository.ChecklistListView> listLight(Integer deptId, Pageable pageable) {
		return repo.findAllByDepartmentId(deptId, pageable);
	}

	public Page<ChecklistRepository.ChecklistListView> search(String q, Integer deptId, Pageable pageable) {
		return repo.searchListView(q, deptId, pageable);
	}

	public Checklist getById(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Checklist not found: " + id));
	}

	public Checklist save(Checklist checklist) {
		return repo.save(checklist);
	}

	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
