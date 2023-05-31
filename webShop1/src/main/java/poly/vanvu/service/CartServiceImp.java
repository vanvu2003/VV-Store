package poly.vanvu.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import poly.vanvu.entity.Cart;
import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.ProductRepository;

@Service
public class CartServiceImp implements CartService{
	@Autowired
	CartRepository cartRepository;
	@Autowired
	HttpSession session;
	@Autowired
	ProductRepository productRepository;
	
	public Cart setCart(@RequestParam("productId")Integer productId,@RequestParam("quantity")Integer quantity) {
		Cart cart = new Cart();
		
		Product prod = productRepository.getById(productId); //tìm sản phẩm để lấy giá sale
		User user = (User) session.getAttribute("user");
		Cart cartByUserAndProdId = cartRepository.findByUserAndProdId(user.getUsername(), productId);
		
		if (cartByUserAndProdId == null) {
			cart.setQuantity(quantity);
			cart.setTotalPrice(quantity * prod.getPriceSale());
			Product product = new Product();
			product.setId(productId);
			
			
			cart.setUser(user);
			cart.setProduct(product);
			return cart;
		}else {
			int quantityCurrent = cartByUserAndProdId.getQuantity() + quantity;
			double totalPriceCurrent = cartByUserAndProdId.getTotalPrice() + (quantity * prod.getPriceSale());
			
			cartByUserAndProdId.setTotalPrice(totalPriceCurrent);
			cartByUserAndProdId.setQuantity(quantityCurrent);
			return cartByUserAndProdId;
		}
		
	}


}
