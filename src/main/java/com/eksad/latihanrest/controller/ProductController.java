package com.eksad.latihanrest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eksad.latihanrest.dao.BrandDao;
import com.eksad.latihanrest.dao.ProductDao;
import com.eksad.latihanrest.model.Brand;
import com.eksad.latihanrest.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController// untuk semua platform seperti json, android dll
@RequestMapping(value = "/api/v1/product")
@Api(tags = "Product")
public class ProductController {
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	BrandDao branDao;
	
	@ApiOperation(
			value = "API to retview all product's data",
			notes = "Return data with JSON Format",
			tags = "Get Data API")
	@GetMapping("/getAll")
	public List<Product> getAll() {
		List<Product>result = new ArrayList<>();
		
		productDao.findAll().forEach(result::add);
		
		return result;
	}
	
	@ApiOperation(
			value = "API to retview all product's data",
			notes = "Return data with JSON Format",
			tags = "Get Data API")
	@GetMapping("getByProductId/{productId}")
	public List<Product> getByProductId(@PathVariable Long productId) {
		List<Product> result = new ArrayList<Product>();
		productDao.findByProductId(productId).forEach(result::add);
		return result;
	}
	
	@ApiOperation(
			value = "Add new Product data",
			notes = "Add new Product data to database",
			tags = "Data Manipulation API")
	@PostMapping(value = "add")
	public Product add (@RequestBody Product product) {
		Brand brand = branDao.findById(product.getBrandId()).orElse(null);
		if (brand != null) {
			product.setBrand(brand);
			return productDao.save(product);
		}
		return null;
//		return productDao.save(product);
	}
	
	@ApiOperation(
			value = "Update new Product data",
			notes = "Update new Product data to database",
			tags = "Data Manipulation API")
	@PutMapping(value ="update/{id}")
	public String update(@RequestBody Product product,@PathVariable Long id) {
		Product productSelected = productDao.findById(id).orElse(null);
		
		if(productSelected != null) {
			productSelected.setName(product.getName());
			productSelected.setBrand(product.getBrand());
			productSelected.setPrice(product.getPrice());
			
			productDao.save(productSelected);
			return "Succesful";
		}else {
			return "Failed";
		}
	}
	
	@ApiOperation(
			value = "Delete Product data",
			notes = "Delete Product data to database",
			tags = "Data Manipulation API")
	@DeleteMapping(value ="delete/{id}")
	public HashMap<String, Object> delete(@PathVariable Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		productDao.deleteById(id);
		result.put("message","Successfully removed");
		return result;
	}
	
	
	@ApiOperation(
			value = "API to retview product's data by name",
			notes = "Return data with JSON Format",
			tags = "Get Data API")
	@GetMapping(value ="search/{name}")
	public List<Product> search(@PathVariable String name){
		List<Product> result = new ArrayList<Product>();
		productDao.findByName(name).forEach(result::add);
		
		return result;	
	}
}
