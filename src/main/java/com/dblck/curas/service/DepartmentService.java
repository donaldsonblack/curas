package com.dblck.curas.service;

import com.dblck.curas.dto.department.DepartmentCreateRequest;
import com.dblck.curas.dto.department.DepartmentUpdateRequest;
import com.dblck.curas.model.Department;
import com.dblck.curas.model.UserDepartment;
import com.dblck.curas.repository.DepartmentRepository;
import com.dblck.curas.repository.UserDepartmentRepository;
import com.dblck.curas.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

	private final DepartmentRepository repo;
	private final UserDepartmentRepository membershipRepo;
	private final UserRepository userRepo;
	private final CacheManager cacheManager;

	private static final String DEPT_CACHE = "deptById";

	public Page<Department> list(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Cacheable(value = DEPT_CACHE, key = "#p0")
	public Optional<Department> getById(Integer id) {
		return repo.findById(id);
	}

	@CacheEvict(value = DEPT_CACHE, key = "#p0")
	public void delete(Integer id) {
		var members = membershipRepo.findAllByDepartmentId(id);

		Cache membershipsByDepartment = cacheManager.getCache("membershipsByDepartment");
		if (membershipsByDepartment != null) {
			membershipsByDepartment.evict(id);
		}

		Cache membershipsByUser = cacheManager.getCache("membershipsByUser");
		Cache userInDept = cacheManager.getCache("userInDept");
		Cache authz = cacheManager.getCache("authz");

		for (UserDepartment m : members) {
			Integer userId = m.getUser().getId();
			if (membershipsByUser != null) {
				membershipsByUser.evict(userId);
			}
			if (userInDept != null) {
				userInDept.evict("dept:" + id + ":user:" + userId);
			}
			if (authz != null) {
				userRepo.findById(userId).ifPresent(u -> {
					if (u.getSub() != null) {
						authz.evict("dept:" + id + ":sub:" + u.getSub());
					}
				});
			}
		}

		repo.deleteById(id);
	}

	@CachePut(value = DEPT_CACHE, key = "#result.id", unless = "#result == null")
	public Department save(DepartmentCreateRequest req) {
		Department d = Department.builder()
				.name(req.name())
				.location(req.location())
				.parent(req.parent())
				.build();
		return repo.save(d);
	}

	@CacheEvict(value = DEPT_CACHE, key = "#p0")
	public Optional<Department> update(Integer id, DepartmentUpdateRequest req) {
		return repo.findById(id)
				.map(
						dep -> {
							req.name().ifPresent(dep::setName);
							req.location().ifPresent(dep::setLocation);
							req.parent().ifPresent(dep::setParent);
							return repo.save(dep);
						});
	}

	@CacheEvict(value = DEPT_CACHE, key = "#p0")
	public Optional<Department> put(Integer id, DepartmentCreateRequest req) {
		return repo.findById(id)
				.map(
						dep -> {
							dep.setName(req.name());
							dep.setLocation(req.location());
							dep.setParent(req.parent());
							return repo.save(dep);
						});
	}
}
