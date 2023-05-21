package com.sba.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sba.payloads.ApiResponse;
import com.sba.payloads.CommentDto;
import com.sba.services.CommentService;

@RestController
@RequestMapping("/v1/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/add/{postId}")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto comment,@PathVariable Integer postId){
		
		CommentDto cretedComment= this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(cretedComment,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer id){
		
		this.commentService.deleteComment(id); 
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment "+id+" deleted succesfully",true),HttpStatus.OK);
		
	}

}
