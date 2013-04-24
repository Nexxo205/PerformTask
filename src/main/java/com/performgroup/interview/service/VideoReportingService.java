package com.performgroup.interview.service;

import java.util.Collection;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

public interface VideoReportingService {

	Collection<VideoReportingBean> countByDate();
	Collection<VideoReportingBean> countByType();
	VideoReportingBean countForType(String videoType);
	
}
