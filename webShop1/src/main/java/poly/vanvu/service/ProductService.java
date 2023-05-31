package poly.vanvu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import poly.vanvu.dto.ProductDto;
import poly.vanvu.entity.Category;
import poly.vanvu.entity.Color;
import poly.vanvu.entity.Product;
import poly.vanvu.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	public Product save(ProductDto productDto) {
		Product prod = new Product();
		
		prod.setId(productDto.getId());
		
		if (prod.getId() != 0) {
			Product prodCurrent = productRepository.findById(prod.getId()).orElse(null);
			
			if (prodCurrent.getImage()== null || prodCurrent.getImage().equals("")) {
				prod.setImage(productDto.getImage().getOriginalFilename());
			}else {
				prod.setImage(prodCurrent.getImage());
			}
		}else {
			prod.setImage(productDto.getImage().getOriginalFilename());
		}
		prod.setName(productDto.getName());
		prod.setPriceImport(productDto.getPriceImport());
		prod.setPrice(productDto.getPrice());
		prod.setPriceSale(productDto.getPriceSale());
		prod.setRom(productDto.getRom());
		
		Color color = new Color();
		color.setId(productDto.getColor());
		
		Category cate = new Category();
		cate.setId(productDto.getCategory());
		
		prod.setColor(color);
		prod.setCategory(cate);
		prod.setSource(productDto.getSource());
		prod.setGuarantee(productDto.getGuarantee());
		prod.setQuantity(productDto.getQuantity());
		prod.setDescription(productDto.getDescription());
		prod.setCreateDate(new Date());
		prod.setCode(productDto.getCode());
		
		if (productDto.getQuantity()>= 5) {
			prod.setStatus("Đang bán");
		}else if (productDto.getQuantity() < 5 && productDto.getQuantity() > 0) {
			prod.setStatus("Sắp hết hàng");
		}else {
			prod.setStatus("Ngưng bán");
		}
		return productRepository.save(prod);
	}
	
	public void fillProduct(Model model) {
		List<Product> listProd = productRepository.findAllProd();
		
		model.addAttribute("listProd",listProd);
	}
}
