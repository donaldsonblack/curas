package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.model.*;
import dev.donaldsonblack.cura.repository.*;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("authzService")
@AllArgsConstructor
public class AuthzService {

  private final UserRepository userRepository;
  private final EquipmentRepository equipmentRepository;
  private final UserDepartmentRepository userDepartmentRepository;

  public boolean userInDepartment(Integer deptId, Integer userId) {
    if (userDepartmentRepository.existsById(new UserDepartmentId(userId, deptId))) {
      return true;
    } else {
      return false;
    }
  }

  public boolean userInDepartmentOfEquipment(Integer userId, Integer equipId) {
    Equipment equip = equipmentRepository.getReferenceById(equipId);
    Integer deptId = equip.getDepartmentId();

    if (userInDepartment(deptId, userId)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean userInDepartmentBySub(Integer deptId, UUID sub) {
    Optional<User> u = userRepository.findBySub(sub);

    if (u.isEmpty()) {
      return false;
    }
    Integer id = u.get().getId();

    if (userInDepartment(deptId, id)) {
      return true;
    } else {
      return false;
    }
  }
}
