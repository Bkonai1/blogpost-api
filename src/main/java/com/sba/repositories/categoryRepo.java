package com.sba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sba.entities.Category;

public interface categoryRepo extends JpaRepository<Category, Integer> {

}
