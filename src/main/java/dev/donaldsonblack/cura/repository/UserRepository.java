package dev.donaldsonblack.cura.repository;

import dev.donaldsonblack.cura.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {}
