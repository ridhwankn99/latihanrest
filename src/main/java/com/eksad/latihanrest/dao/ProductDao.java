package com.eksad.latihanrest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eksad.latihanrest.model.Product;

public interface ProductDao extends CrudRepository<Product, Long>{
	
//	@Query("select p from  Product p Where p.brand.id = :brandId")
//	public Iterable<Product> findByBrandId(@Param ("brandId") Long brandId);
	
	@Query("select p from Product p where p.id = :productId")
	public Iterable<Product> findByProductId(@Param ("productId") Long productId);
	
	public List<Product> findByName(String name);


}
