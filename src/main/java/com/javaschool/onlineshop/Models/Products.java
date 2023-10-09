package com.javaschool.onlineshop.Models;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Products")
@Data
@NoArgsConstructor
public class Products {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID product_uuid;
	
	@OneToOne
    @JoinColumn(name = "product_category_uuid")
    private Category categoty;
	
	@ManyToOne
	@JoinColumn(name = "product_platform_uuid")
	private Platforms platform;

	@Column(name = "product_title")
	private String title;
	
	@Column(name = "product_price")
	private Double price;
	
	@Column(name = "product_weight")
	private Double weight;
	
	@Column(name = "product_stock")
	private Integer stock;
	
	@Column(name = "product_PEGI")
	private Integer PEGI;
	
	@Column(name = "product_isDigital")
	private Boolean isDigital;
	
	@Column(name = "product_description")
	private String description;
	
	@Column(name = "product_image")
	private String image;
				
	@Column(name = "product_isDeleted")
	private Boolean isDeleted;
	
	//RELATIONS
	@OneToMany(mappedBy = "product", cascade= CascadeType.ALL)
	private List<ProductsGenre> products_genre;
	
	@OneToMany(mappedBy = "ProductOrders", cascade= CascadeType.ALL)
	private List<OrderProducts> product_orders;
}
