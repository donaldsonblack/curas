package dev.donaldsonblack.cura.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.donaldsonblack.cura.config.JsonViews;
import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.service.UserDepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserDepartmentController {

  private final UserDepartmentService service;

  @Transactional(readOnly = true)
  @GetMapping("/user/{userId}/department")
  @JsonView(JsonViews.departmentMinimal.class)
  public List<UserDepartment> listForUser(@PathVariable Integer userId) {
    return service.listMembershipForUser(userId);
  }

  @Transactional(readOnly = true)
  @GetMapping("/department/{deptId}/members")
  @JsonView(JsonViews.userMinimal.class)
  @PreAuthorize("@authzService.userInDepartmentBySub(#deptId, authentication.token.claims['sub'])")
  public List<UserDepartment> listMembers(@PathVariable Integer deptId) {
    return service.membershipsForDepartment(deptId);
  }
}
