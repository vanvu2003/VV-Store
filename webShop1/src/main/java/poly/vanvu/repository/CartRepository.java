package poly.vanvu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.vanvu.entity.Cart;
import poly.vanvu.entity.User;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findByUser(User user);
	
	@Query("SELECT c FROM Cart c WHERE c.user.username LIKE ?1 AND c.product.id = ?2")
	Cart findByUserAndProdId(String username, int prodId);
	
	@Query("SELECT COUNT(c.id) FROM Cart c WHERE c.user.username LIKE ?1")
	int countCartByUser(String username);
}
