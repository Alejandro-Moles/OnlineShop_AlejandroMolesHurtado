package com.javaschool.onlineshop.Models;
import java.util.List;
import lombok.Data;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Countries")
@Data
@NoArgsConstructor
public class Country {
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID country_uuid; 
	
	@Column(name = "country_name")
	private String name;
	
	@Column(name ="country_isDeleted")
	private boolean isDeleted;
	
	//RELATION
	@OneToMany(mappedBy = "country", cascade= CascadeType.ALL)
	private List<City> cities;
}
