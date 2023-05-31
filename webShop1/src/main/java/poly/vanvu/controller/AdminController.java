package poly.vanvu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import poly.vanvu.dto.BrandDto;
import poly.vanvu.dto.ProductDto;
import poly.vanvu.entity.Category;
import poly.vanvu.entity.Color;
import poly.vanvu.entity.Product;
import poly.vanvu.repository.BrandRepository;
import poly.vanvu.repository.ColorRepository;
import poly.vanvu.repository.ProductRepository;
import poly.vanvu.service.BrandServiceImp;
import poly.vanvu.service.ColorService;
import poly.vanvu.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ColorRepository colorRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	ServletContext app;
	@Autowired
	BrandServiceImp brandServiceImp;
	@Autowired
	ColorService colorService;
	@Autowired
	ProductService productService;
	
	@ModelAttribute("product")
	public ProductDto productDto() {
		return new ProductDto();
	}
	@ModelAttribute("brand")
	public BrandDto brandDto() {
		return new BrandDto();
	}
	@ModelAttribute("color")
	public Color color() {
		return new Color();
	}
	
	@RequestMapping("/product")
	public String home(Model model) {
//		ProductDto productDto = new ProductDto();
//		model.addAttribute("product",productDto);
		fillAllCate(model);
		colorService.fillColor(model);
		return "admin/index";
	}
	
	@PostMapping("/product/save")
	public String saveProd(@RequestParam("image")MultipartFile image,@ModelAttribute("product")ProductDto prodDto) throws IllegalStateException, IOException {
		if (!image.isEmpty()) {
			String filename= image.getOriginalFilename();
			File folder = new File(app.getRealPath("/upload"));
			if (!folder.exists()) {
				folder.mkdir();
			}
			File file = new File(app.getRealPath("/upload/"+ filename));
			image.transferTo(file);
		}
		
		productService.save(prodDto);
		return "redirect:/admin/product";
	}

	
	@RequestMapping("/product/list")
	public String table(Model model,@RequestParam("index")Optional<Integer> index) {
		int count =  (int) productRepository.count();
		
		int countPage = (int) Math.ceil((double) count / 5);
		
		int active;
		if (!index.isEmpty()) {
			active= index.get();
			index = Optional.of(index.get()-1);
		}else {
			active=1;
		}
		
		Pageable pageable = PageRequest.of(index.orElse(0), 5);

		Page<Product> listProd = productRepository.findAllProducts(pageable);
		model.addAttribute("countPage",countPage);
		model.addAttribute("listProd",listProd);
		model.addAttribute("a",active);
		
		return "admin/listProd";
	}
	@PostMapping("/product/edit")
	public String editProd(@RequestParam("id")int id,Model model) {
		Product prod = productRepository.findById(id).orElse(null);
		
		fillAllCate(model);
		colorService.fillColor(model);
		
		model.addAttribute("product",prod);
		
		return "admin/index";
	}
	@PostMapping("/product/list/delete/{id}")
	public String delete(@PathVariable("id")Integer id,Model model) {
		Product prod = productRepository.findById(id).orElse(null);
		
		productRepository.delete(prod);
		return "redirect:/admin/product/list";
	}
//	@ResponseBody
//	public void fillProduct(Model model) {
//		List<Product> listProd = productRepository.findAll();
//		model.addAttribute("listProd",listProd);
//	}
	
	@RequestMapping("/color")
	public String color(Model model) {
		colorService.fillColor(model);
		
		return "admin/color";
	}
	
	@PostMapping("/color/save")
	public String addColor(@ModelAttribute("color")Color color) {
		colorService.save(color);
		return "redirect:/admin/color";
	}
	@GetMapping("/color/edit")
	public String editColor(@RequestParam("id")Integer id,Model model) {
		
		Optional<Color> listColors = colorRepository.findById(id);
		
		if (listColors != null) {
			Color color = listColors.orElse(null); 
			model.addAttribute("color",color);
			colorService.fillColor(model);
		}
		model.addAttribute("id",id);
		return "admin/color";
	}
	@GetMapping("/color/delete")
	public String deleteColor(@RequestParam("id")Integer id,Model model) {
		Optional<Color> listColors = colorRepository.findById(id);
		
		if (listColors != null) {
			Color color = listColors.orElse(null); 
			
			colorRepository.delete(color);
			colorService.fillColor(model);
		}
		return "redirect:/admin/color";
		
	}
	
	//Phần brand
	@RequestMapping("/brand")
	public String brand(Model model) {
		fillAllCate(model);

		return "admin/brand";
	}
	
	@ResponseBody
	public void fillAllCate(Model model){
		List<Category> listCate = brandRepository.findAll();
		model.addAttribute("listCate",listCate);
	}
	
	@PostMapping("/brand/save")
	public String add(@RequestParam("image")MultipartFile image,@ModelAttribute("brand")BrandDto brandDto,HttpSession session) throws IllegalStateException, IOException {
		if(!image.isEmpty()) {	
			String filename= image.getOriginalFilename();
			File folder = new File(app.getRealPath("/upload"));
			if (!folder.exists()) {
				folder.mkdir();
			}
			File file = new File(app.getRealPath("/upload/"+ filename));
			image.transferTo(file);
		}
		
		brandServiceImp.save(brandDto);
		
		return "redirect:/admin/brand";
	}
	
	@GetMapping("/brand/edit")
	public String editBrand(@RequestParam("id")Integer id,Model model,HttpSession session) {
		
		Category brand = brandRepository.findById(id).orElse(null);
		
		model.addAttribute("brand",brand);
		fillAllCate(model);
		session.setAttribute("image", brand.getImage());
		model.addAttribute("id",id);
		return "admin/brand";
	}
	
	@GetMapping("/brand/delete")
	public String deleteBrand(@RequestParam("id")Integer id,Model model) {
		Optional<Category> listBrand = brandRepository.findById(id);
		
		if (listBrand != null) {
			Category brand = listBrand.orElse(null); 
			
			brandRepository.delete(brand);
			fillAllCate(model);
		}
		return "redirect:/admin/brand";
		
	}
}
