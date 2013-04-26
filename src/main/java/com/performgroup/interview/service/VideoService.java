package com.performgroup.interview.service;

import java.util.Collection;

import com.performgroup.interview.domain.Video;

/**
 * A service layer to retrieve video information 
 */
public interface VideoService {
	
	Video getVideo(Integer videoId);

	Collection<Video> listVideos();

	void addVideo(Video video);

	void deleteVideo(Video video);
	
	Collection<Video> findTitle(String title);
	
	Collection<Video> findType(String type);
}
