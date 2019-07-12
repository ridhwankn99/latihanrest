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
import com.eksad.latihanrest.model.Brand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/brand")
@Api(tags = "Brand")
public class BrandController {
	
	@Autowired
	BrandDao brandDao;
	
	@ApiOperation(
			value = "API to retview all brand's data",
			notes = "Return data with JSON Format",
			tags = "Get Data API")
	@GetMapping("/getAll")
	public List<Brand> getAll() {
		List<Brand>result = new ArrayList<>();
		
		brandDao.findAll().forEach(result::add);
		
		return result;
	}
	
	@ApiOperation(
			value = "API to retview brand's data by id",
			notes = "Return data with JSON Format",
			tags = "Get Data API")
	@GetMapping("getOne/{id}") //wajib id
	public  Brand getOne(@PathVariable Long id) {
		return brandDao.findById(id).orElse(null);
	}
	
	@ApiOperation(
			value = "Add new Brand data",
			notes = "Add new Brand data to database",
			tags = "Data Manipulation API")
	@PostMapping(value = "add/{id}")
	public Brand add(@RequestBody Brand brand) { //req body berfungsi untuk ngebaca data apa yang akan dikirim dalambentuk brand
		try {
			return brandDao.save(brand);
			//return "Berhsail Tersimpan";
		} catch (Exception e) {
			e.printStackTrace();
			//return "Gagal Tersimpan";
			return null;
		}
	}
	
	@ApiOperation(
			value = "Update new Brand data",
			notes = "Update new Brand data to database",
			tags = "Data Manipulation API")
	@PutMapping(value = "update/{id}")
	public Brand update(@RequestBody Brand brand, @PathVariable Long id) {
		Brand brandSelected = brandDao.findById(id).orElse(null);
		if(brandSelected !=null) {
			brandSelected.setName(brand.getName());
			brandSelected.setProductType(brand.getProductType());
			
			
			return brandDao.save(brandSelected);
			//return "Berhasil Memperbaharui";
		}else {
			return null;
			//return "Gagal Memperbaharui";
		}
	}
	
	@ApiOperation(
			value = "Delete Brand data",
			notes = "Delete Brand data to database",
			tags = "Data Manipulation API")
	@DeleteMapping(value = "delete/{id}")
	public HashMap<String, Object> delete(@PathVariable Long id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		brandDao.deleteById(id);
		result.put("message", "Berhasil dihapus");
		return result;
	}
}
