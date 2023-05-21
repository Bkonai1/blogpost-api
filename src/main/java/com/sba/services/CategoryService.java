package com.sba.services;

import java.util.List;

import com.sba.payloads.UserDto;
import com.sba.payloads.categoryDto;

public interface CategoryService {

	

	categoryDto createCategory(categoryDto category);
	
	categoryDto updateCategory(categoryDto category, Integer categoryId);
	
	categoryDto getcategoryById(Integer categoryId);
	
	List<categoryDto> getAllCategories();
	
	void deleteCategory(Integer categoryId);
}
