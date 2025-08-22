package dev.donaldsonblack.cura.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
	List<T> findAll();

	Optional<T> findById(ID id);

	boolean deleteById(ID id);

	boolean update(T entity);

	boolean insert(T entity);
}
