package com.sba.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sba.entities.Category;
import com.sba.entities.Post;
import com.sba.entities.User;
import com.sba.exceptions.ResourceNotFoundException;
import com.sba.payloads.PostDto;
import com.sba.payloads.PostResponse;
import com.sba.payloads.UserDto;
import com.sba.payloads.categoryDto;
import com.sba.repositories.PostRepo;
import com.sba.repositories.UserRepo;
import com.sba.repositories.categoryRepo;
import com.sba.services.PostService;



@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private categoryRepo catRepo;
	
	@Autowired
	private ModelMapper modelMap;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer catId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		Category category = this.catRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		Post post= this.dtoToPost(postDto);
		post.setUser(user);
		post.setCategory(category); 
		Post savedPost= this.postRepo.save(post); 
		
		return this.postToDto(savedPost);
	}

	@Override
	public PostDto updatePost(PostDto post, Integer postId) {
		
		Post u= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","Id",postId));
		
		u.setAddDate(new Date());
     // u.setCategory(post.getCategory());
		u.setContent(post.getContent()); 
		u.setImageName(post.getImageName()); 
		u.setTitle(post.getTitle());
	//	u.setUser(post.getUser()); 
		u=this.postRepo.save(u); 
		PostDto savedPost= this.postToDto(u);
		return savedPost;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		
		Post u= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","Id",postId));
		System.out.println(u.toString());
		PostDto fetchedpost= this.postToDto(u);
		return fetchedpost;
	}

	@Override
	public PostResponse getAllPosts(Integer pageNo,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable p= PageRequest.of(pageNo, pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);

		List<PostDto> postList= pagePost.getContent().stream().map((u)-> this.postToDto(u)).collect(Collectors.toList()); 
		
		PostResponse postResp= new PostResponse();
		postResp.setContent(postList); 
		postResp.setPageNumber(pagePost.getNumber()); 
		postResp.setLastPage(pagePost.isLast()); 
		postResp.setPageSize(pagePost.getSize());
		postResp.setTotalElements(pagePost.getTotalElements()); 
		postResp.setTotalPages(pagePost.getTotalPages()); 
		return postResp;
	}

	@Override
	public void deletePost(Integer postId) {

		Post u= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		this.postRepo.delete(u);

	}
	
    public Post dtoToPost(PostDto postDto) {
		
    	Post post = this.modelMap.map(postDto, Post.class); 
	
		
		return post;
	 }
	
     public PostDto postToDto(Post post) {
    	 PostDto postDto= this.modelMap.map(post, PostDto.class);
    	
   
    	return postDto;
		
	  }

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		 List<Post>post =this.postRepo.findAllByUser(user);
		 List<PostDto> postDto =post.stream().map((u)->this.modelMap.map(u, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}
 
	@Override
	public List<PostDto> getAllPostsByCategory(Integer catId) {


		Category category = this.catRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		 List<Post>post =this.postRepo.findAllByCategory(category); 
		 List<PostDto> postDto =post.stream().map((u)->this.modelMap.map(u, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {

         List<Post> posts=this.postRepo.searchByTitle("%"+keyword+"%");
         List<PostDto> postDto =posts.stream().map((u)->this.modelMap.map(u, PostDto.class)).collect(Collectors.toList());
 		
 		return postDto;
	}
	
	

}
