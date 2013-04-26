package com.performgroup.interview.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ManyToAny;

/**
 * A POJO representing a video in the Perform system.
 */

@Entity
@SequenceGenerator(name = "VID_SEQ_GEN", sequenceName = "VIDSEQ", allocationSize = 1)
public class Video implements Serializable {

	private static final long serialVersionUID = 2284488937952510797L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VID_SEQ_GEN")
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "title", unique = false, nullable = false)
	private String title;
	@Column(name = "video_path", unique = false, nullable = false)
	private String videoPath;
	@Column(name = "video_type", unique = false, nullable = false)
	@Enumerated(value = EnumType.STRING)
	private VideoType videoType;
	@Column(name = "creation_date", unique = false, nullable = false)
	private Date creationDate;	
	@ManyToMany (fetch=FetchType.EAGER)
	@JoinTable
	(name="video_tags", joinColumns = @JoinColumn(name = "video_id"), inverseJoinColumns = @JoinColumn(name = "tag_name"))
	private Collection<Tag> tags;

	// Default Constructor
	public Video() {

	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	
	public VideoType getVideoType() {
		return videoType;
	}

	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}

}
