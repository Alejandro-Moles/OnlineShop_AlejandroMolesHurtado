package com.javaschool.onlineshop.Models;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Roles")
@Data
@NoArgsConstructor
public class Role {
	
	//COLUMNS
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID role_uuid; 
	
	@Column(name = "role_type")
	private String type;
	
	@Column(name ="role_isDeleted")
	private boolean isDeleted;
}
