package dev.donaldsonblack.cura.controller;

import dev.donaldsonblack.cura.model.User;
import dev.donaldsonblack.cura.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping
  public Page<User> getAll(Pageable pageable) {
    return service.list(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User create(@RequestBody User user) {
    return service.save(user);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable Integer id) {
    service.delete(id);
  }
}
