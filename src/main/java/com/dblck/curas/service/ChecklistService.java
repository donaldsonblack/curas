package com.dblck.curas.service;

import com.dblck.curas.dto.checklist.ChecklistCreateRequest;
import com.dblck.curas.dto.checklist.ChecklistTableView;
import com.dblck.curas.dto.checklist.ChecklistUpdateRequest;
import com.dblck.curas.model.Checklist;
import com.dblck.curas.repository.ChecklistRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistService {

	private final ChecklistRepository repo;
	private static final String CHECKLIST_CACHE = "checklistById";

	public Page<Checklist> list(Pageable pageable) {
		return repo.findAll(pageable);
	}

	public Page<ChecklistRepository.ChecklistListView> listLight(Integer deptId, Pageable pageable) {
		return repo.findAllByDepartmentId(deptId, pageable);
	}

	public Page<ChecklistRepository.ChecklistListView> search(
			String q, Integer deptId, Pageable pageable) {
		return repo.searchListView(q, deptId, pageable);
	}

  public Page<ChecklistTableView> tableView(Pageable pageable) {
    return repo.tableViewPage(pageable);
  }

	@Cacheable(value = CHECKLIST_CACHE, key = "#p0")
	public Optional<Checklist> getById(Integer id) {
		return repo.findById(id);
	}

	@CacheEvict(value = CHECKLIST_CACHE, key = "#p0")
	public void delete(Integer id) {
		repo.deleteById(id);
	}

	@CachePut(value = CHECKLIST_CACHE, key = "#result.id", unless = "#result == null")
	public Checklist save(ChecklistCreateRequest req) {
		Checklist c = Checklist.builder()
				.name(req.name())
				.description(req.description())
				.type(req.type())
				.departmentId(req.deptId())
				.equipmentId(req.equipId())
				.authorId(req.authId())
				.questions(req.questions())
				.build();

		return repo.save(c);
	}

	@CacheEvict(value = CHECKLIST_CACHE, key = "#p0")
	public Optional<Checklist> patch(Integer id, ChecklistUpdateRequest req) {
		return repo.findById(id)
				.map(
						cl -> {
							req.name().ifPresent(cl::setName);
							req.description().ifPresent(cl::setDescription);
							req.type().ifPresent(cl::setType);
							req.deptId().ifPresent(cl::setDepartmentId);
							req.equipId().ifPresent(cl::setEquipmentId);
							req.authId().ifPresent(cl::setAuthorId);
							req.questions().ifPresent(cl::setQuestions);
							return repo.save(cl);
						});
	}

	@CacheEvict(value = CHECKLIST_CACHE, key = "#p0")
	public Optional<Checklist> put(Integer id, ChecklistCreateRequest req) {
		return repo.findById(id)
				.map(
						cl -> {
							cl.setName(req.name());
							cl.setDescription(req.description());
							cl.setType(req.type());
							cl.setDepartmentId(req.deptId());
							cl.setEquipmentId(req.equipId());
							cl.setAuthorId(req.authId());
							cl.setQuestions(req.questions());
							return repo.save(cl);
						});
	}
}
