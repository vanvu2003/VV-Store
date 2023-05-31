package poly.vanvu.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Oders")
@NamedQuery(name="Oder.findAll", query="SELECT o FROM Oder o")
public class Oder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="createDate", columnDefinition = "DATE")
	private Date createDate;
	
	@Column(name = "status", columnDefinition = "BIT")
	private boolean status;
	
	@OneToMany(mappedBy = "order")
	@JsonIgnore
	private List<OrderDetail> orderDetail;

	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;

	

}