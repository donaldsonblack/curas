package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findBySub(UUID sub);
}
