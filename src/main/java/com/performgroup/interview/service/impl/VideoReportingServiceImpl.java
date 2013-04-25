package com.performgroup.interview.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import com.performgroup.interview.dao.VideoReportingDAO;
import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.service.VideoReportingService;

public class VideoReportingServiceImpl implements VideoReportingService{
	
	private VideoReportingDAO videoReportDAO;
	
	
	public VideoReportingDAO getVideoReportDAO() {
		return videoReportDAO;
	}

	@Resource
	public void setVideoReportDAO(VideoReportingDAO videoReportDAO) {
		this.videoReportDAO = videoReportDAO;
	}

	public Collection<VideoReportingBean> countByDate() {
		return  getVideoReportDAO().countByDay();
	}

	public Collection<VideoReportingBean> countByType() {		
		return getVideoReportDAO().countByVideoType();
	}

	
	/**
	 * Calls dao method with value of video type converted from string.
	 * If no such value exists in VideoType enum, calls dao method with null
	 */
	public VideoReportingBean countForType(String videoTypeString) {
		
		VideoType[] typevalues = VideoType.values();
		VideoType videoType = null;
		
		for (VideoType type : typevalues) {
			if (videoTypeString.equals(type.toString())){
				videoType=type;
			}
		}
				
		return getVideoReportDAO().countForVideoType(videoType);
		
	}


}
