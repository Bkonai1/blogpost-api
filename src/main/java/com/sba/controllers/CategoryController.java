package com.sba.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sba.payloads.ApiResponse;
import com.sba.payloads.categoryDto;
import com.sba.services.CategoryService;

@RestController

@RequestMapping("v1/api/category")
public class CategoryController {
	

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<categoryDto> createUser(@Valid @RequestBody categoryDto category){
		
		categoryDto cretedCat= this.categoryService.createCategory(category);
		return new ResponseEntity<>(cretedCat,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<categoryDto> findUserId(@PathVariable Integer id ){
		
		categoryDto cretedCategory= this.categoryService.getcategoryById(id);
		return new ResponseEntity<categoryDto>(cretedCategory,HttpStatus.FOUND);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<categoryDto> updateUser(@Valid @RequestBody categoryDto category,@PathVariable Integer id){
		
		categoryDto cretedCat= this.categoryService.updateCategory(category, id); 
		return ResponseEntity.ok(cretedCat); 
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
		
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category "+id+" deleted succesfully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<List<categoryDto>> findAllUser( ){
		
		List<categoryDto> cretedCAtegory= this.categoryService.getAllCategories();
		return new ResponseEntity<List<categoryDto>>(cretedCAtegory,HttpStatus.FOUND);
		
	}


}
