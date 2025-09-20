package dev.donaldsonblack.cura.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.donaldsonblack.cura.model.Role;
import dev.donaldsonblack.cura.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findBySub(UUID sub);

	@Query("select u.role from User u where u.sub = :sub")
	Optional<Role> findRoleBySub(@Param("sub") UUID sub);

	@Query("select u.id from User u where u.sub = :sub")
	Optional<Integer> findIdBySub(@Param("sub") UUID sub);
}
