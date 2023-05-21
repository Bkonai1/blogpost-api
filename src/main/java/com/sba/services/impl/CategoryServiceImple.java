package com.sba.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sba.entities.Category;
import com.sba.entities.User;
import com.sba.exceptions.ResourceNotFoundException;
import com.sba.payloads.UserDto;
import com.sba.payloads.categoryDto;
import com.sba.repositories.categoryRepo;
import com.sba.services.CategoryService;


@Service
public class CategoryServiceImple implements CategoryService {
	
	@Autowired
	private categoryRepo catRepo;
	
	@Autowired
	private ModelMapper modelMap;

	@Override
	public categoryDto createCategory(categoryDto category) {
		
		Category cat1= this.dtoToCat(category);
		Category savedCat= this.catRepo.save(cat1); 
		
		return this.catToDto(savedCat); 
	}

	@Override
	public categoryDto updateCategory(categoryDto category, Integer categoryId) {


		Category u= this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		
		u.setCategoryDescription(category.getCategoryDescription()); 
		u.setCategoryId(category.getCategoryId()); 
		u.setCategoryTitle(category.getCategoryTitle());
		u=this.catRepo.save(u); 
		categoryDto savedCat= this.catToDto(u);
		return savedCat;
	}

	@Override
	public categoryDto getcategoryById(Integer categoryId) {


		Category u= this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryId));
		System.out.println(u.toString());
		categoryDto savedCategory= this.catToDto(u);
		return savedCategory;
	}

	@Override
	public List<categoryDto> getAllCategories() {


		List<categoryDto> userList= this.catRepo.findAll().stream().map((u)-> this.catToDto(u)).collect(Collectors.toList()); 
		return userList;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category u= this.catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		this.catRepo.delete(u);

	}
	
    public Category dtoToCat(categoryDto catDto) {
		
    	Category cat = this.modelMap.map(catDto, Category.class); 
	
		
		return cat;
	 }
	
     public categoryDto catToDto(Category cat) {
    	 categoryDto catDto= this.modelMap.map(cat, categoryDto.class);
    	
   
    	return catDto;
		
	  }

}
