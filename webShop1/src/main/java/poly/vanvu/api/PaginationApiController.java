package poly.vanvu.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import poly.vanvu.dto.PaginationDto;
import poly.vanvu.entity.Product;
import poly.vanvu.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class PaginationApiController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/pagination")
	public PaginationDto pagination(@RequestParam("page")Optional<Integer> page,
											@RequestParam("minPrice")double minPrice,
											@RequestParam("maxPrice")double maxPrice){
		PaginationDto paginationDto = new PaginationDto();
		if (!page.isEmpty()) {
			page = Optional.of(page.get() - 1);
		} else {
			page = Optional.of(1);
		}
		int countProd = productRepository.countProdFilter(minPrice, maxPrice);
		Pageable pageable = PageRequest.of(page.orElse(0), 9);
		
		Page<Product> listProd = productRepository.filterProdByPrice(minPrice, maxPrice, pageable);
		
		paginationDto.setPage(page.orElse(null));
		paginationDto.setTotalPage((int) Math.ceil((double) countProd / 9));
		paginationDto.setListResult(listProd.toList());
		
		return paginationDto;
	}
}
