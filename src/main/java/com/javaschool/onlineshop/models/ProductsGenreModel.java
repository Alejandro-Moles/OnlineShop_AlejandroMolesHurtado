package com.javaschool.onlineshop.models;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name="Products_has_Genre")
@Data
@NoArgsConstructor
public class ProductsGenreModel {

	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID productsGenreUuid;

	@ManyToOne
	@JoinColumn(name = "product_uuid")
	private ProductsModel product;

	@ManyToOne
	@JoinColumn(name = "genre_uuid")
	private GenreModel genre;

	@Column(name = "isDeleted")
	private Boolean isDeleted;
}