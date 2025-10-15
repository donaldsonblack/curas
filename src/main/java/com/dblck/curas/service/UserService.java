package com.dblck.curas.service;

import com.dblck.curas.dto.user.UserCreateRequest;
import com.dblck.curas.dto.user.UserUpdateRequest;
import com.dblck.curas.model.User;
import com.dblck.curas.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;
  private static final String USER_CACHE = "userById";

  public Page<User> list(Pageable pageable) {
    return repo.findAll(pageable);
  }

  @Cacheable(value = USER_CACHE, key = "#p0")
  public Optional<User> getById(Integer id) {
    return repo.findById(id);
  }

  @CachePut(value = USER_CACHE, key = "#result.id", unless = "#result == null")
  public User save(UserCreateRequest req) {
    User user =
        User.builder()
            .fname(req.fname())
            .lname(req.lname())
            .email(req.email())
            .sub(req.sub())
            .build();

    return repo.save(user);
  }

  @CacheEvict(value = USER_CACHE, key = "#p0")
  public void delete(Integer id) {
    repo.deleteById(id);
  }

  @CacheEvict(value = USER_CACHE, key = "#p0")
  public Optional<User> updateUser(Integer id, UserUpdateRequest req) {
    return repo.findById(id)
        .map(
            user -> {
              req.fname().ifPresent(user::setFname);
              req.lname().ifPresent(user::setLname);
              req.email().ifPresent(user::setEmail);
              req.sub().ifPresent(user::setSub);

              return repo.save(user);
            });
  }

  @CacheEvict(value = USER_CACHE, key = "#p0")
  public Optional<User> putUser(Integer id, UserCreateRequest req) {
    return repo.findById(id)
        .map(
            user -> {
              user.setFname(req.fname());
              user.setLname(req.lname());
              user.setEmail(req.email());
              user.setSub(req.sub());

              return repo.save(user);
            });
  }
}
