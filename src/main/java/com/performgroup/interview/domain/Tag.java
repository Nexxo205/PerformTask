package com.performgroup.interview.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "id", nullable= false)
	private Integer id;
	@Id
	@Column (name="tag_name", nullable = false)
	private String name;
	@ManyToMany(mappedBy="tags")	
	private Collection<Video> videos;
	
	public Tag(String name){
		this.name = name;
	}
	
	public Tag(){
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Collection<Video> getVides() {
		return videos;
	}
	public void setVides(Collection<Video> video) {
		this.videos = video;
	}
	

}
