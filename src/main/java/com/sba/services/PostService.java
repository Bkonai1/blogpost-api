package com.sba.services;

import java.util.List;

import com.sba.entities.Post;
import com.sba.payloads.PostDto;
import com.sba.payloads.PostResponse;

public interface PostService {
	
   PostDto createPost(PostDto Post,Integer userId,Integer catId);
	
	PostDto updatePost(PostDto post, Integer postId);
	
	PostDto getPostById(Integer postId);
	
	PostResponse getAllPosts(Integer pageNo,Integer pageSize,String sortBy,String sortDir);
	
	void deletePost(Integer postId);
	
	List<PostDto> getAllPostsByUser(Integer userId);
	
	List<PostDto> getAllPostsByCategory(Integer catId);
	
	List<PostDto> searchPost(String keyword );

}
