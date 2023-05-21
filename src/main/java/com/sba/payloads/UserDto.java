package com.sba.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.*;
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    
	private int id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Size(min=4, max=10,message="max 10 char min 4 chars allowed")
	private String password;
	
	@Email
	private String email;
	
	@NotEmpty
	private String about;

}
