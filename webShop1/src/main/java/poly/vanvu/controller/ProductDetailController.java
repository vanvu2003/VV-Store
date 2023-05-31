package poly.vanvu.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.ProductRepository;
import poly.vanvu.service.BrandServiceImp;

@Controller
public class ProductDetailController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	BrandServiceImp brandServiceImp;
	@Autowired
	HttpSession session;
	@Autowired
	CartRepository cartRepository;
	
	@GetMapping("/dien-thoai/{brand}")
	public String findByBrand(@PathVariable("brand")String brand,Model model,HttpSession session,@RequestParam("index")Optional<Integer> index) {
		int count = (int) productRepository.countProdByBrand(brand);

		int countPage = (int) Math.ceil((double) count / 9);

		int active;
		if (!index.isEmpty()) {
			active = index.get();
			index = Optional.of(index.get() - 1);
		} else {
			active = 1;
		}

		Pageable pageable = PageRequest.of(index.orElse(0), 9);
		
		
		Page<Product> listProdByBrand = productRepository.findProdByBrand(brand,pageable);
		
		model.addAttribute("listProd",listProdByBrand);
		brandServiceImp.fillAllCate(model);
		session.setAttribute("checkNull", listProdByBrand);
		model.addAttribute("act",active);
		model.addAttribute("countPage",countPage);
		model.addAttribute("urlFilterBrand","/"+brand);
		countCart(model);
		return "shop";
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
