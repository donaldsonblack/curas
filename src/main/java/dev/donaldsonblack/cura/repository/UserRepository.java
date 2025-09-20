package dev.donaldsonblack.cura.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.donaldsonblack.cura.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findBySub(UUID sub);
}
