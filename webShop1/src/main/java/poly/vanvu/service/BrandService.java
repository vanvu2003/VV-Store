package poly.vanvu.service;

import poly.vanvu.dto.BrandDto;
import poly.vanvu.entity.Category;

public interface BrandService {
	Category save(BrandDto brandDto);
}
