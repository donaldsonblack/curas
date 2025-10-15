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
import lombok.RequiredArgsConstructor;
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
				return membershipRepo.save(existing);
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

		return membershipRepo.save(d);
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
	}
}
