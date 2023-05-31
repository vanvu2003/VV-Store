package poly.vanvu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import poly.vanvu.dto.BrandDto;
import poly.vanvu.entity.Category;
import poly.vanvu.repository.BrandRepository;

@Service
public class BrandServiceImp implements BrandService{
	@Autowired
	BrandRepository brandRepository;
	
	@Override
	public Category save(BrandDto brandDto) {
		Category cate = new Category();
		
		cate.setId(brandDto.getId());
		cate.setName(brandDto.getName());
//		BeanUtils.copyProperties(brandDto, cate);
		if(cate.getId() != 0) {
			Category cateCurrent = brandRepository.findById(cate.getId()).orElse(null);
			
			if (cateCurrent.getImage()== null || cateCurrent.getImage().equals("")) {
				cate.setImage(brandDto.getImage().getOriginalFilename());
			}else {
				cate.setImage(cateCurrent.getImage());
			}
		}else {
			cate.setImage(brandDto.getImage().getOriginalFilename());
		}
		
		return brandRepository.save(cate);
	}
	
	public void fillAllCate(Model model) {
		List<Category> listCate = brandRepository.findAll();
		
		model.addAttribute("listCate",listCate);
	}
}
