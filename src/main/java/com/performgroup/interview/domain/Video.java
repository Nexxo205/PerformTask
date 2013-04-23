package com.performgroup.interview.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * A POJO representing a video in the Perform system. 
*/

@Entity
@SequenceGenerator(
    name="VID_SEQ_GEN",
    sequenceName="VIDSEQ",
    allocationSize=1
)
public class Video implements Serializable {

	private static final long serialVersionUID = 2284488937952510797L;

	private Long id;
	private String title;
	private String videoPath;
	private VideoType videoType;
	private Date creationDate;

	// Default Constructor
	public Video(){

	}

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="VID_SEQ_GEN")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", unique = false, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "video_path", unique = false, nullable = false)
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	@Column(name = "video_type", unique = false, nullable = false)
	@Enumerated(value=EnumType.STRING)
	public VideoType getVideoType() {
		return videoType;
	}

	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}

	@Column(name = "creation_date", unique = false, nullable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", videoPath="
				+ videoPath + ", videoType=" + videoType + ", creationDate="
				+ creationDate + "]";
	}

}
