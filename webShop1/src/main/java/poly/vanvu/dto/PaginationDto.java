package poly.vanvu.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.vanvu.entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
	private int page;
	private int totalPage;
	private List<Product> listResult = new ArrayList<>();
	
}
