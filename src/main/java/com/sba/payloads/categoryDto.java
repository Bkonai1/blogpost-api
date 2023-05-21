package com.sba.payloads;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class categoryDto {
	
    private Integer categoryId;
	
	private String categoryTitle;
	
	private String categoryDescription;

}
