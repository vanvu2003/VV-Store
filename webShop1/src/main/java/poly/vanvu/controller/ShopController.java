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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.vanvu.entity.Product;
import poly.vanvu.entity.User;
import poly.vanvu.repository.CartRepository;
import poly.vanvu.repository.ProductRepository;
import poly.vanvu.service.BrandServiceImp;
import poly.vanvu.service.ProductService;

@Controller
@RequestMapping("/dien-thoai")
public class ShopController {
	@Autowired
	ProductService productService;
	@Autowired
	BrandServiceImp brandServiceImp;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	HttpSession session;
	@Autowired
	CartRepository cartRepository;
	
	@RequestMapping()
	public String shop(Model model,@RequestParam("index")Optional<Integer> index) {
		int count = (int) productRepository.count();

		int countPage = (int) Math.ceil((double) count / 9);

		int active;
		if (!index.isEmpty()) {
			active = index.get();
			index = Optional.of(index.get() - 1);
		} else {
			active = 1;
		}

		Pageable pageable = PageRequest.of(index.orElse(0), 9);
		
		Page<Product> listProd = productRepository.findAllProducts(pageable);
		
//		productService.fillProduct(model);
		brandServiceImp.fillAllCate(model);
		
		model.addAttribute("act",active);
		model.addAttribute("listProd",listProd);
		model.addAttribute("countPage",countPage);
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
	@GetMapping("/")
	public String sortByPrice(@RequestParam("sort") String sort, Model model,@RequestParam("index")Optional<Integer> index) {
		int count = (int) productRepository.count();

		int countPage = (int) Math.ceil((double) count / 9);

		int active;
		if (!index.isEmpty()) {
			active = index.get();
			index = Optional.of(index.get() - 1);
		} else {
			active = 1;
		}
		Pageable pageable = PageRequest.of(index.orElse(0), 9);
		Page<Product> listProd;
		
		if (sort.equals("gia-thap-den-cao")) {
//			List<Product> listProd = productRepository.findProductsByPriceSale();
			listProd = productRepository.findProductsByPriceSale(pageable);
			model.addAttribute("listProd", listProd);
			model.addAttribute("urlFilter","/?sort=gia-thap-den-cao");
			
		} else if (sort.equals("gia-cao-den-thap")) {
			listProd = productRepository.findProductsBySortPriceDesc(pageable);
			model.addAttribute("listProd", listProd);
			model.addAttribute("urlFilter","/?sort=gia-cao-den-thap");
		}

		model.addAttribute("act",active);
		model.addAttribute("countPage",countPage);
		brandServiceImp.fillAllCate(model);
		return "shop";
	}
	
	@GetMapping("/filter")
	public String filterProdByPrice(@RequestParam("minPrice")double minPrice,@RequestParam("maxPrice")double maxPrice,
			@RequestParam("index")Optional<Integer> index,Model model) {
		
		int count = (int) productRepository.countProdFilter(minPrice, maxPrice);

		int countPage = (int) Math.ceil((double) count / 9);

		int active;
		if (!index.isEmpty()) {
			active = index.get();
			index = Optional.of(index.get() - 1);
		} else {
			active = 1;
		}
		Pageable pageable = PageRequest.of(index.orElse(0), 9);
		Page<Product> listProd = productRepository.filterProdByPrice(minPrice, maxPrice, pageable);
		
		model.addAttribute("listProd", listProd);
		model.addAttribute("act",active);
		model.addAttribute("countPage",countPage);
		brandServiceImp.fillAllCate(model);
		
		return "shop";
	}
}
