package com.dblck.curas.service;

import com.dblck.curas.model.*;
import com.dblck.curas.repository.*;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service("authzService")
@AllArgsConstructor
public class AuthzService {

  private static final String AUTHZ_CACHE = "authz";
  private static final String USER_DEPT_CACHE = "userInDept";

  private final UserRepository userRepository;
  private final EquipmentRepository equipmentRepository;
  private final UserDepartmentRepository userDepartmentRepository;

  @Cacheable(value = USER_DEPT_CACHE, key = "'dept:' + #p0 + ':user:' + #p1")
  public boolean userInDepartment(Integer deptId, Integer userId) {
    return userDepartmentRepository.existsById(new UserDepartmentId(userId, deptId));
  }

  @Cacheable(value = USER_DEPT_CACHE, key = "'equip:' + #p1 + ':user:' + #p0")
  public boolean userInDepartmentOfEquipment(Integer userId, Integer equipId) {
    Equipment equip = equipmentRepository.getReferenceById(equipId);
    Integer deptId = equip.getDepartmentId();
    return userInDepartment(deptId, userId);
  }

  @Cacheable(value = AUTHZ_CACHE, key = "'dept:' + #p0 + ':sub:' + #p1")
  public boolean userInDepartmentBySub(Integer deptId, UUID sub) {
    Optional<User> u = userRepository.findBySub(sub);
    if (u.isEmpty()) {
      return false;
    }
    Integer id = u.get().getId();
    return userInDepartment(deptId, id);
  }

  @Caching(evict = {
      @CacheEvict(value = USER_DEPT_CACHE, allEntries = true),
      @CacheEvict(value = AUTHZ_CACHE, allEntries = true)
  })
  public void clearAuthzCaches() {
    // Used to manually clear authorization caches when memberships or departments change.
  }
}
