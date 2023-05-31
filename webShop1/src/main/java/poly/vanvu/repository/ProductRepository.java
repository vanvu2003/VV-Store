package poly.vanvu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.vanvu.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	//phân trang cho trang shop
	@Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);
	
	//phân trang khi sắp xếp
	@Query("SELECT p FROM Product p order by priceSale desc")
    Page<Product> findProductsBySortPriceDesc(Pageable pageable);
	
	//phân trang khi sắp xếp
	@Query("SELECT p FROM Product p order by priceSale")
    Page<Product> findProductsByPriceSale(Pageable pageable);
	
	//Phân trang sản phẩm theo brand
	@Query("SELECT p FROM Product p where p.category.name LIKE ?1")
	Page<Product> findProdByBrand(String brand,Pageable pageable);
		
	//Tìm sản phẩm theo mã code
	List<Product>  findByCode(String code);
	
	//Tìm sản phẩm randum ngẫu nhiên
	@Query("SELECT p FROM Product p ORDER BY NEWID()")
    List<Product> findAllProd();
	
//	@Query("SELECT p FROM Product p order by priceSale")
//    List<Product> findProductsByPriceSale();
	
//	@Query("SELECT p FROM Product p order by priceSale desc")
//    List<Product> findProductsByPriceSaleDesc();
	
	//Tìm ra 5 sản phẩm (trừ sản phẩm đang chọn) ngẫu nhiên
	@Query("SELECT p FROM Product p where id != ?1 ORDER BY NEWID()")
    List<Product> findProdTop5(int id);
	
	
	//Tìm sản phẩm theo id và mã code
	@Query("SELECT p FROM Product p where p.color.id = ?1 AND p.code = ?2")
	Product findByCodeAndColor(int color,String code);
	
	//Đếm số sản phẩm theo brand
	@Query("SELECT count(p.id) FROM Product p where p.category.name like ?1")
	int countProdByBrand(String brandName);
	
	//Lọc theo kiểu ipa
//	@Query("SELECT p FROM Product p where p.priceSale >= ?1 AND p.priceSale < ?2")
//	List<Product> filterProdByPrice(double min, double max);
	
	@Query("SELECT p FROM Product p where p.priceSale >= ?1 AND p.priceSale < ?2")
	Page<Product> filterProdByPrice(double min, double max,Pageable pageable);
	
	@Query("SELECT COUNT(p.id) FROM Product p where p.priceSale >= ?1 AND p.priceSale < ?2")
	int countProdFilter(double min, double max);
}
