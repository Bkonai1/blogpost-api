package com.sba.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sba.entities.Category;
import com.sba.entities.Comment;
import com.sba.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	    private int postId;
		
		@NotEmpty
		private String title;
		
		@NotEmpty
		@Size(min=4, max=100,message="max 100 char min 4 chars allowed")
		private String content;
		
		private String imageName;
		
		@NotEmpty
		private Date addDate;
		
		private UserDto user;
		
		private categoryDto category;
		
		private Set<Comment> comments= new HashSet<>();

}
