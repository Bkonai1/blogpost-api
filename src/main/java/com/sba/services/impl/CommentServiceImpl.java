package com.sba.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sba.entities.Comment;
import com.sba.entities.Post;
import com.sba.exceptions.ResourceNotFoundException;
import com.sba.payloads.CommentDto;
import com.sba.repositories.CommentRepo;
import com.sba.repositories.PostRepo;
import com.sba.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMap;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {


		Post u= this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","Id",postId));
		Comment c= this.modelMap.map(commentDto, Comment.class);
		c.setPost(u);
		Comment savedComment= this.commentRepo.save(c);
		return this.modelMap.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {


		Comment u= this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","Id",commentId));
		this.commentRepo.delete(u);

	}

}
