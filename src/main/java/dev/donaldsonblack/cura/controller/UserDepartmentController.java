package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.model.UserDepartment;
import dev.donaldsonblack.cura.service.UserDepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserDepartmentController {

  private final UserDepartmentService service;

  @Transactional(readOnly = true)
  @GetMapping("/{userId}/department")
  public List<UserDepartment> listForUser(@PathVariable Integer userId) {
    return service.listMembershipForUser(userId);
  }
}
