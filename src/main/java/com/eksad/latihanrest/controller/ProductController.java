package com.eksad.latihanrest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eksad.latihanrest.dao.BrandDao;
import com.eksad.latihanrest.dao.ProductDao;
import com.eksad.latihanrest.model.Brand;
import com.eksad.latihanrest.model.Product;

@RestController// untuk semua platform seperti json, android dll
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	BrandDao branDao;
	
	@RequestMapping("getByProductId/{productId}")
	public List<Product> getByProductId(@PathVariable Long productId) {
		List<Product> result = new ArrayList<Product>();
		productDao.findByProductId(productId).forEach(result::add);
		return result;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Product save (@RequestBody Product product) {
		Brand brand = branDao.findById(product.getBrandId()).orElse(null);
		if (brand != null) {
			product.setBrand(brand);
			return productDao.save(product);
		}
		return null;
//		return productDao.save(product);
	}
	@RequestMapping(value ="update/{id}",method = RequestMethod.PUT)
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
	
	@RequestMapping(value ="delete/{id}", method = RequestMethod.DELETE)
	public HashMap<String, Object> delete(@PathVariable Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		productDao.deleteById(id);
		result.put("message","Successfully removed");
		return result;
	}
	@RequestMapping(value ="search/{name}", method = RequestMethod.GET)
	public List<Product> search(@PathVariable String name){
		List<Product> result = new ArrayList<Product>();
		productDao.findByName(name).forEach(result::add);
		
		return result;	
	}
}
