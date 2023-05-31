package poly.vanvu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import poly.vanvu.entity.User;

@Repository
public interface UserRespository extends JpaRepository<User, String>{
	List<User> findByUsername(String id);
	@Query("SELECT u FROM User u WHERE u.email LIKE ?1")
	List<User> findByEmail(String email);
	
}
