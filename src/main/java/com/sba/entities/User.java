package com.sba.entities;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	

	@Column(name="user_name", nullable=false,length=100)
	private String name;
	
	
	private String password;
	
	private String email;
	private String about;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL, fetch= FetchType.EAGER)
	private List<Post> postList= new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name="user_role", joinColumns=@JoinColumn(name="user",referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="role",referencedColumnName="id"))
	private Set<Role> roles= new HashSet<>();
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", about=" + about
				+ "]";
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> auth=this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return null;
	}



	@Override
	public String getUsername() {
		
		return this.email;
	}



	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}



	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
