package com.performgroup.interview.dao;

import java.util.Collection;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

/**
 * The JDBC implementation of the reporting DAO.
 * Needs to remain JDBC driven for the purpose of this task (as opposed to Hibernate).
 */
public class VideoReportingJDBCDAO implements VideoReportingDAO {

	public Collection<VideoReportingBean> countByDay() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<VideoReportingBean> countByVideoType() {
		// TODO Auto-generated method stub
		return null;
	}

	public VideoReportingBean countForVideoType(VideoType videoType) {
		// TODO Auto-generated method stub
		return null;
	}

}
