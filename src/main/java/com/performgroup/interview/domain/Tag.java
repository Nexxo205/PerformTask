package com.performgroup.interview.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {
	
	private Integer id;
	
	private String name;
	
	@Id @GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column (name="tag_name", nullable = false, unique = true )
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
