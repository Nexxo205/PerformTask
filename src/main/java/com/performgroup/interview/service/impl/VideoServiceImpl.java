package com.performgroup.interview.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.performgroup.interview.dao.VideoDAO;
import com.performgroup.interview.domain.Video;
import com.performgroup.interview.service.VideoService;

public class VideoServiceImpl implements VideoService {
	
	private VideoDAO videoDAO;
	
	public VideoDAO getVideoDAO() {
		return videoDAO;
	}
	
	@Resource
	public void setVideoDAO(VideoDAO videoDAO) {
		this.videoDAO = videoDAO;
	}

	public void addVideo(Video video) {
		getVideoDAO().save(video);
	}

	public void deleteVideo(Video video) {
		getVideoDAO().delete(video);
	}

	public Video getVideo(Integer videoId) {
		return getVideoDAO().findById(videoId);
	}

	public Collection<Video> listVideos() {
		return getVideoDAO().findAll();
	}
	
	public Collection<Video> findTitle(String title){
		return getVideoDAO().findByTitle(title);
	}

	public Collection<Video> findType(String type) {
		return getVideoDAO().findByType(type);		
	}

}
