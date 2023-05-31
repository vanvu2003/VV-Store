package poly.vanvu.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "createDate", columnDefinition = "DATE")
	private Date createDate;

	@Column(name = "description", columnDefinition = "NVARCHAR(500)")
	private String description;
	
	private int guarantee;
	
	@Column(name = "rom", columnDefinition = "INTEGER")
	private int rom;

	@Column(name = "code", columnDefinition = "NVARCHAR(50)")
	private String code;
	
	private String image;

	@Column(name = "name", columnDefinition = "NVARCHAR(100)")
	private String name;
	
	@Column(name = "quantity", columnDefinition = "INTEGER")
	private int quantity;

	private double price;

	private double priceSale;
	
	private double priceImport;

	@Column(name = "source", columnDefinition = "NVARCHAR(50)")
	private String source;
	
	@Column(name = "status", columnDefinition = "NVARCHAR(50)")
	private String status;

	@OneToMany(mappedBy="product")
	@JsonIgnore
	private List<OrderDetail> orderDetail;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<Cart> cart;
	
	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId")
	@JsonIgnore
	private Category category;

	@ManyToOne
	@JoinColumn(name="colorId")
	@JsonIgnore
	private Color color;

	
}