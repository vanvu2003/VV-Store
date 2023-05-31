package poly.vanvu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.vanvu.dto.CartDto;
import poly.vanvu.entity.Cart;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.service.BrandServiceImp;

@Controller
public class CartController {
	@Autowired
	BrandServiceImp brandServiceImp;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	HttpSession session;
	
	@RequestMapping("/cart")
	public String viewCart(Model model) {
		fillCart(model);
		countCart(model);
		brandServiceImp.fillAllCate(model);
		return "cart";
	}
	
	@ResponseBody
	public void fillCart(Model model) {
		User user = (User) session.getAttribute("user");
		List<Cart> listCart = cartRepository.findByUser(user);
		
		model.addAttribute("listCart",listCart);
	}
	
	@ResponseBody
	public int countCart(Model model) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			int cartNumber =  cartRepository.countCartByUser(user.getUsername());
			model.addAttribute("cartNumber",cartNumber);
			return cartNumber;
		}
		return 0;
	}
	
	
}
