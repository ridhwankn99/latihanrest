package com.eksad.latihanrest.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Product {
//	======================Pak Singgih=======================
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
//				generator = "product_id")
//	@SequenceGenerator(name = "product_id", sequenceName = "product_id_seq"
//			, allocationSize = 1) // DIPAKE KALO GA ADA DEFAULT VALUE DI TABLE
	
	@ApiModelProperty(value = "ID(Primary Key)")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
//	@ManyToOne(fetch = FetchType.LAZY)
	@ApiModelProperty(value = "Brand Name", dataType = "String", required = true)
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ApiModelProperty(value = "Brand ID")
	@Transient
	private Long brandId;
	
	@ApiModelProperty(value = "Product Name")
	@Column(nullable = false)
	private String name;
	
	@ApiModelProperty(value = "Price")
	@Column(nullable = false)
	private BigDecimal price;
	
}
