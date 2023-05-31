package poly.vanvu.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.entity.Cart;
import poly.vanvu.repository.CartRepository;

@RestController
public class CheckOutApiController {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	HttpSession session;
	
	@PostMapping("/api/order")
	public void order(@RequestParam("id")Integer id) {
		List<Integer> listIdCart = new ArrayList<>();
		
		
		listIdCart.add(id);
		session.setAttribute("listCart", listIdCart);
		
		List<Integer> abc = (List<Integer>) session.getAttribute("listCart");
		System.out.println(abc);
	}
}
