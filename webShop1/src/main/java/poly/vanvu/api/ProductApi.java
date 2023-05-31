package poly.vanvu.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.entity.Product;
import poly.vanvu.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductApi {
	private  ProductRepository productRepository;

    @Autowired
    public void ProductApiController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    
    @GetMapping("/changeColor")
    public Product changeColor(@RequestParam("color")Integer color, @RequestParam("code")String code) {
    	Product prod = (Product) productRepository.findByCodeAndColor(color, code);
    	
    	return prod;
    	
    }
}
