package com.sba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sba.entities.Category;
import com.sba.entities.Post;
import com.sba.entities.User;
import com.sba.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findAllByUser(User user);
	List<Post> findAllByCategory(Category category);
	
	//@Query("select p from Post where p.title like :key")
	List<Post> searchByTitle(@Param("key")String title);

}
