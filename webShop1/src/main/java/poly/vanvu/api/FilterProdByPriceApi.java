package poly.vanvu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.entity.Product;
import poly.vanvu.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class FilterProdByPriceApi {
	@Autowired
	ProductRepository productRepository;
	
//	@GetMapping("/filter")
//	public ResponseEntity<List<Product>> filterByPrice(@RequestParam("minPrice")double minPrice, @RequestParam("maxPrice")double maxPrice){
//		List<Product> listProd = productRepository.filterProdByPrice(minPrice, maxPrice);
//		return ResponseEntity.ok(listProd);
//	}
}
