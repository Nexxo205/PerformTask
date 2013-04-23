package com.performgroup.interview.cmd;

import java.io.InputStream;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.service.VideoService;

public class VideoProcessor {

	private transient VideoService videoService;
	
	private transient String videoIngestFolder;
	
	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
	
	public void setVideoIngestFolder(String videoIngestFolder) {
		this.videoIngestFolder = videoIngestFolder;
	}

	/**
	 * Outputs video details to the specified logger
	 * @param logger
	 */
	public void listVideos(Logger logger) {
		
		Collection<Video> videos = videoService.listVideos();
		
		for (Video video : videos) {			
			String videoData = String.format("[%d] - %s / %s - %s", video.getId(), video.getTitle(), video.getVideoType(), video.getVideoPath());
			logger.info(videoData);
		}
	}
	
	/**
	 * Processes a video by ingesting data from XML
	 * @param logger
	 * @param videoFile
	 */
	public void ingestVideo(Logger logger, String videoFile) {
		
		String path = videoIngestFolder+videoFile;
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
		if (in == null) {
			logger.info("Cannot find file");
		}
		else {
			// TODO
		}		
	}

}
