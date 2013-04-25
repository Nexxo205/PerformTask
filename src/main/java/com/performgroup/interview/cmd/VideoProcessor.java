package com.performgroup.interview.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.service.VideoReportingService;
import com.performgroup.interview.service.VideoService;

public class VideoProcessor {

	private transient VideoService videoService;

	private transient String videoIngestFolder;

	private transient VideoReportingService videoReportingService;
	
	private VideoSaxParser saxParser;

	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Resource
	public void setVideoReportingService(
			VideoReportingService videoReportingService) {
		this.videoReportingService = videoReportingService;
	}

	@Resource
	public void setSaxParser(VideoSaxParser saxParser) {
		this.saxParser = saxParser;
	}

	public void setVideoIngestFolder(String videoIngestFolder) {
		this.videoIngestFolder = videoIngestFolder;
	}

	/**
	 * Outputs video details to the specified logger
	 * 
	 * @param logger
	 */
	public void listVideos(Logger logger) {

		Collection<Video> videos = videoService.listVideos();

		for (Video video : videos) {
			String videoData = String.format("[%d] - %s / %s - %s",
					video.getId(), video.getTitle(), video.getVideoType(),
					video.getVideoPath());
			logger.info(videoData);
		}
	}

	/**
	 * Processes a video by ingesting data from XML
	 * 
	 * @param logger
	 * @param videoFile	 
	 */
	public void ingestVideo(Logger logger, String videoFile){

		String path = videoIngestFolder + videoFile;

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(path);

		if (in == null) {
			logger.info("Cannot find file");
		} else {

			Video parsedVideo = saxParser.parseXmlSax(in);			

			if (parsedVideo == null) {				
					System.out.println("Parsed video returned as null! Possible reason is wrong video type!");				
			} else {
				System.out.println("Ingesting video..");
				videoService.addVideo(parsedVideo);
				System.out.println("Video ingested!");
				
			}

		}
	}	

	/**
	 * Calls method for counting videos depending on the day they were ingested,
	 * then calls method that prints output 
	 * 	
	 * @param logger
	 */
	public void countDates(Logger logger) {

		Collection<VideoReportingBean> videoReports = videoReportingService
				.countByDate();
		for (VideoReportingBean videoReportBean : videoReports) {
			videoReportOutput(logger, videoReportBean);
		}
	}

	/**
	 * Calls method for counting videos by all existing video types,
	 * then calls method that prints output 
	 * 
	 * @param logger
	 */
	public void countTypes(Logger logger) {

		Collection<VideoReportingBean> videoReports = videoReportingService
				.countByType();
		for (VideoReportingBean videoReportBean : videoReports) {
			videoReportOutput(logger, videoReportBean);
		}
	}

	/**
	 * Calls method for counting videos by specified video type,
	 * then calls method that prints output
	 * 
	 * @param logger
	 * @param videoType
	 */
	public void countType(Logger logger, String videoType) {

		VideoReportingBean videoReport = videoReportingService
				.countForType(videoType);
		if (videoReport== null){
			System.out.println("Wrong video type!");
		}else
		videoReportOutput(logger, videoReport);
	}

	/**
	 * handles output for objects of VideoReportingBean
	 * 
	 * @param logger
	 * @param videoReportBean 
	 */
	private void videoReportOutput(Logger logger,
			VideoReportingBean videoReportBean) {

		String videoData = String.format("%d - %s ",
				videoReportBean.getCount(), videoReportBean.getDescription());
		logger.info(videoData);

	}

}
