package dev.donaldsonblack.cura.service;

import dev.donaldsonblack.cura.model.User;
import dev.donaldsonblack.cura.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;

  public Page<User> list(Pageable pageable) {
    return repo.findAll(pageable);
  }

  public User getById(Integer id) {
    return repo
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
  }

  public User save(User user) {
    return repo.save(user);
  }

  public void delete(Integer id) {
    repo.deleteById(id);
  }
}
