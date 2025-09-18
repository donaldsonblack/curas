package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.model.Department;
import dev.donaldsonblack.cura.model.User;
import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.model.UserDepartmentId;
import dev.donaldsonblack.cura.repository.DepartmentRepository;
import dev.donaldsonblack.cura.repository.UserDepartmentRepository;
import dev.donaldsonblack.cura.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public List<UserDepartment> listMembershipForUser(Integer userId) {
    if (!userRepo.existsById(userId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    return membershipRepo.findAllByUserId(userId);
  }

  @Transactional(readOnly = true)
  public List<UserDepartment> membershipsForDepartment(Integer deptId) {
    if (!departmentRepo.existsById(deptId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
    }

    return membershipRepo.findAllByDepartmentId(deptId);
  }

  // @Transactional(readOnly = true)
  // public Page<UserDepartment> pagedMemberships(Integer deptId, Pageable page) {
  // 	if (!departmentRepo.existsById(deptId)) {
  // 		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
  // 	}
  //
  // 	return membershipRepo.findAllByDepartmentId(deptId, page);
  // }

  // @Transactional(readOnly = true)
  // public Page<UserDepartment> membershipsForDepartment(
  // Integer departmentId,
  // Pageable pageable) {
  // if (!departmentRepo.existsById(departmentId)) {
  // throw new ResponseStatusException(
  // HttpStatus.NOT_FOUND,
  // "Department not found");
  // }
  //
  // return membershipRepo.findAllByDepartmentId(departmentId, pageable);
  // }

  @Transactional
  public UserDepartment addMembership(Integer userId, Integer departmentId, String role) {
    User user =
        userRepo
            .findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found"));

    Department dept =
        departmentRepo
            .findById(departmentId)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

    var id = new UserDepartmentId(user.getId(), dept.getId());
    var existing = membershipRepo.findById(id).orElse(null);

    if (existing != null) {
      existing.setRole(role == null ? existing.getRole() : role);
      return membershipRepo.save(existing);
    }

    var m = new UserDepartment();
    m.setId(id);
    m.setUser(user);
    m.setDepartment(dept);
    m.setRole(role == null ? "user" : role);
    return membershipRepo.save(m);
  }

  @Transactional
  public void removeMembership(Integer userId, Integer departmentId) {
    var id = new UserDepartmentId(userId, departmentId);
    if (!membershipRepo.existsById(id)) {
      return;
    }
    membershipRepo.deleteById(id);
  }
}
