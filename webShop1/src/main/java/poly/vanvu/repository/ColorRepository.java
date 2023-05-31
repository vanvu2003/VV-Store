package poly.vanvu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.vanvu.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{
	List<Color> findById(int id);

	List<Color> findById(Optional<Integer> id);
}
