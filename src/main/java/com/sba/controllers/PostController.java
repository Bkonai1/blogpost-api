package com.sba.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sba.config.AppConstant;
import com.sba.entities.Category;
import com.sba.entities.User;
import com.sba.payloads.ApiResponse;
import com.sba.payloads.PostDto;
import com.sba.payloads.PostResponse;
import com.sba.payloads.UserDto;
import com.sba.payloads.categoryDto;
import com.sba.services.CategoryService;
import com.sba.services.FileService;
import com.sba.services.PostService;
import com.sba.services.UserService;

@RestController
@RequestMapping("v1/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/add/userId/{uId}/catId/{cId}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post,@PathVariable Integer uId ,@PathVariable Integer cId  ){
		
	
		PostDto cretedPost= this.postService.createPost(post,uId,cId);
		return new ResponseEntity<>(cretedPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/find/userId/{uId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer uId){
		
	
		List<PostDto> fetchededPost= this.postService.getAllPostsByUser(uId);
		return new ResponseEntity<List<PostDto>>(fetchededPost,HttpStatus.OK);
		
	}
	
	@GetMapping("/find/categoryId/{cId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer cId){
		
	
		List<PostDto> fetchedPost= this.postService.getAllPostsByCategory(cId);
		return new ResponseEntity<List<PostDto>>(fetchedPost,HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<PostResponse> grtAllPost(@RequestParam(value="pageNo", defaultValue=AppConstant.PAGE_NUMBER, required=false)Integer pageNo,
			@RequestParam(value="pageSize", defaultValue=AppConstant.PAGE_SIZE, required=false)Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstant.SORT_BY, required=false)String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstant.SORT_DIR, required=false)String sortDir){
		
		PostResponse fetchedPost= this.postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(fetchedPost,HttpStatus.FOUND);
		
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<PostDto> findPostId(@PathVariable Integer id ){
		
		PostDto fetchedPost= this.postService.getPostById(id);
		return new ResponseEntity<PostDto>(fetchedPost,HttpStatus.FOUND);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id){
		
		this.postService.deletePost(id); 
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post "+id+" deleted succesfully",true),HttpStatus.OK);
		
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto post,@PathVariable Integer id){
		
		PostDto fetchedPost= this.postService.updatePost(post, id); 
		return ResponseEntity.ok(fetchedPost); 
		
	}
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords ){
		
		List<PostDto> fetchedPost= this.postService.searchPost(keywords); 
		return new ResponseEntity<List<PostDto>>(fetchedPost,HttpStatus.FOUND);
		
	}
	
	// post image upload
	
	@PostMapping("/image/upload/{id}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer id)throws IOException{
		
		String fileName = this.fileService.uploadImage(path, image);
		PostDto fetchedPost= this.postService.getPostById(id);
		fetchedPost.setImageName(fileName); 
		PostDto updatePost= this.postService.updatePost(fetchedPost, id); 
		return ResponseEntity.ok(updatePost); 
		
	}
	
	@GetMapping(value="/image/{imageName}", produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response )
	throws IOException{
		
		InputStream is= this.fileService.getResource(path, imageName); 
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is, response.getOutputStream()); 
		
	}

}
