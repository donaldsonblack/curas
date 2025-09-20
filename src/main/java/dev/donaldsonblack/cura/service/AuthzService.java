package dev.donaldsonblack.cura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import dev.donaldsonblack.cura.model.*;
import dev.donaldsonblack.cura.repository.*;
import lombok.*;

@Service("authz")
@RequiredArgsConstructor
public class AuthzService {
	private final UserRepository userRepo;
	private final ChecklistRepository checklistRepo;
	private final EquipmentRepository equipmentRepository;
	private final UserDepartmentRepository userDeptRepo;

	public UUID sub(Authentication a) {
		Jwt j = (Jwt) a.getPrincipal();
		return UUID.fromString(j.getSubject());
	}

	@Transactional(readOnly = true)
	public boolean isAdmin(Authentication a) {
		UUID s = sub(a);
		if (s == null) {
			return false;
		}
		return userRepo.findRoleBySub(s).orElse(Role.USER) == Role.ADMIN;
	}

	@Transactional(readOnly = true)
	public boolean isSelfOrAdmin(Integer userId, Authentication a) {
		if (isAdmin(a)) {
			return true;
		}
		UUID s = sub(a);
		if (s == null) {
			return false;
		}
		return userRepo.findIdBySub(s).map(id -> id.equals(userId)).orElse(false);
	}

	@Transactional(readOnly = true)
	public boolean inDept(Integer deptId, Authentication a) {
		return isAdmin(a) || userDeptRepo.isMember(sub(a).toString(), deptId);
	}

	@Transactional(readOnly = true)
	public boolean isDeptManager(Integer deptId, Authentication a) {
		return isAdmin(a) || userDeptRepo.isManager(sub(a).toString(), deptId);
	}

	@Transactional(readOnly = true)
	public boolean canReadDept(Integer deptId, Authentication a) {
		return inDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean canWriteInDept(Integer deptId, Authentication a) {
		return inDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean canManageDept(Integer deptId, Authentication a) {
		return isDeptManager(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean inDepByChecklistId(Integer checklistId, Authentication a) {
		Integer deptId = checklistRepo.findDeptId(checklistId);
		return deptId != null && inDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean canWriteChecklist(Integer checklistId, Authentication a) {
		Integer deptId = checklistRepo.findDeptId(checklistId);
		return deptId != null && canWriteInDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean canManageChecklist(Integer checklistId, Authentication a) {
		Integer deptId = checklistRepo.findDeptId(checklistId);
		return deptId != null && canManageDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean inDeptByEquipment(Integer equipmentId, Authentication a) {
		Integer deptId = equipmentRepository.findDeptId(equipmentId);
		return deptId != null && inDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean canWriteEquipment(Integer equipmentId, Authentication a) {
		Integer deptId = equipmentRepository.findDeptId(equipmentId);
		return deptId != null && canWriteInDept(deptId, a);
	}

	@Transactional(readOnly = true)
	public boolean canManageEquipment(Integer equipmentId, Authentication a) {
		Integer deptId = equipmentRepository.findDeptId(equipmentId);
		return deptId != null && canManageDept(deptId, a);
	}
}
