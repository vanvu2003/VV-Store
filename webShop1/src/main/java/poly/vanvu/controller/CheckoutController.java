package poly.vanvu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.vanvu.entity.Cart;
import poly.vanvu.repository.CartRepository;

@Controller
public class CheckoutController {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/checkout")
	public String checkout(@RequestParam(value = "idCart") int[] idCart) {
		List<Cart> listCart = new ArrayList<>();

		for (int i = 0; i < idCart.length; i++) {
			int j = idCart[i];
			Cart cart = cartRepository.getById(j);
			listCart.add(cart);
		}
		session.setAttribute("listCartCheckout", listCart);

		return "checkout";
	}
}
