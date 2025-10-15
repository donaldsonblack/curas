package com.dblck.curas.service;

import com.dblck.curas.model.Department;
import com.dblck.curas.model.Role;
import com.dblck.curas.model.User;
import com.dblck.curas.model.UserDepartment;
import com.dblck.curas.model.UserDepartmentId;
import com.dblck.curas.repository.DepartmentRepository;
import com.dblck.curas.repository.UserDepartmentRepository;
import com.dblck.curas.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserDepartmentService {

	private final UserDepartmentRepository membershipRepo;
	private final UserRepository userRepo;
	private final DepartmentRepository departmentRepo;
	private final CacheManager cacheManager;

	@Transactional(readOnly = true)
	@Cacheable(value = "membershipsByUser", key = "#p0")
	public List<UserDepartment> listMembershipForUser(Integer userId) {
		if (!userRepo.existsById(userId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		return membershipRepo.findAllByUserId(userId);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "membershipsByDepartment", key = "#p0")
	public List<UserDepartment> membershipsForDepartment(Integer deptId) {
		if (!departmentRepo.existsById(deptId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
		}
		return membershipRepo.findAllByDepartmentId(deptId);
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "membershipsByUser", key = "#p0"),
			@CacheEvict(value = "membershipsByDepartment", key = "#p1")
	})
	public UserDepartment addMembership(Integer userId, Integer departmentId, Role role) {
		UserDepartmentId id = new UserDepartmentId(userId, departmentId);

		UserDepartment existing = membershipRepo.findById(id).orElse(null);
		if (existing != null) {
			if (role != null && role != existing.getRole()) {
				existing.setRole(role);
				UserDepartment saved = membershipRepo.save(existing);
				evictAuthzCaches(userId, departmentId);
				return saved;
			}
			return existing;
		}

		if (!userRepo.existsById(userId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found");
		}
		if (!departmentRepo.existsById(departmentId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
		}

		User user = userRepo.getReferenceById(userId);
		Department dept = departmentRepo.getReferenceById(departmentId);

		UserDepartment d = new UserDepartment();
		d.setId(id);
		d.setUser(user);
		d.setDepartment(dept);
		d.setRole(role != null ? role : Role.member);

		UserDepartment saved = membershipRepo.save(d);
		evictAuthzCaches(userId, departmentId);
		return saved;
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "membershipsByUser", key = "#p0"),
			@CacheEvict(value = "membershipsByDepartment", key = "#p1")
	})
	public void removeMembership(Integer userId, Integer departmentId) {
		var id = new UserDepartmentId(userId, departmentId);
		if (!membershipRepo.existsById(id)) {
			return;
		}
		membershipRepo.deleteById(id);
		evictAuthzCaches(userId, departmentId);
	}

	private void evictAuthzCaches(Integer userId, Integer deptId) {
		Cache userInDept = cacheManager.getCache("userInDept");
		if (userInDept != null) {
			userInDept.evict("dept:" + deptId + ":user:" + userId);
			userInDept.clear();
		}
		Cache authz = cacheManager.getCache("authz");
		if (authz != null) {
			Optional<User> u = userRepo.findById(userId);
			if (u.isPresent() && u.get().getSub() != null) {
				UUID sub = u.get().getSub();
				authz.evict("dept:" + deptId + ":sub:" + sub);
			}
		}
	}
}
