package poly.vanvu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import poly.vanvu.entity.Category;

@Repository
public interface BrandRepository extends JpaRepository<Category, Integer>{
	List<Category> findByName(String name);
}
