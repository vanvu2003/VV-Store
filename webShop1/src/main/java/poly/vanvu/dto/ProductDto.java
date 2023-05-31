package poly.vanvu.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private int id;
	private String name;
	private double priceImport;
	private double price;
	private double priceSale;
	private int rom;
	private int color;
	private String source;
	private int guarantee;
	private int category;
	private int quantity;
	private MultipartFile image;	
	private String description;
	private String code;
}
