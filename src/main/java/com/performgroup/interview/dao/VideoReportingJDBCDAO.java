package com.performgroup.interview.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.performgroup.interview.domain.VideoReportingBean;
import com.performgroup.interview.domain.VideoType;

/**
 * The JDBC implementation of the reporting DAO. Needs to remain JDBC driven for
 * the purpose of this task (as opposed to Hibernate).
 */
public class VideoReportingJDBCDAO extends JdbcDaoSupport implements
		VideoReportingDAO {

	/**
	 * Ths method counts videos that were ingested at the same date
	 */
	public Collection<VideoReportingBean> countByDay() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<VideoReportingBean> resultBeans = new ArrayList<VideoReportingBean>();

		List<Map> listOfDates = getJdbcTemplate().queryForList(
				"select distinct(cast(creation_date as date)) from video;");

		for (Map distinctDate : listOfDates) {

			Collection<Date> timestampValuesList = 
					distinctDate.values();
			Date value = timestampValuesList.iterator().next();

			Date valueNextDay = new Date(value.getTime() + 24
					* 60 * 60 * 1000);// + 1 day

			String sql = "select count(*) from video where cast(creation_date as date) = DATE\'"
					+ sdf.format(value) + "\';";

			Integer count = getJdbcTemplate().queryForInt(sql);

			VideoReportingBean videoBean = new VideoReportingBean();
			videoBean.setCount(count);
			videoBean.setDescription("igested " + sdf.format(value));

			resultBeans.add(videoBean);

		}

		return resultBeans;
	}

	/**
	 * This method returns count of all videos of same type, using all values of
	 * enum VideoType and private method countSecificType
	 */
	public Collection<VideoReportingBean> countByVideoType() {
		List<VideoReportingBean> resultBeans = new ArrayList<VideoReportingBean>();

		VideoType[] typevalues = VideoType.values();

		for (VideoType videoType : typevalues) {
			VideoReportingBean videoBean = countSpecificType(videoType);
			resultBeans.add(videoBean);
		}

		return resultBeans;
	}

	/**
	 * This method returns count of all videos of same type, using value
	 * specified by user and private method countSecificType
	 */
	public VideoReportingBean countForVideoType(VideoType videoType) {
		return countSpecificType(videoType);
	}

	/**
	 * This method counts all videos of same type, using specified value if
	 * wrong VideoType was requester returns null
	 */
	private VideoReportingBean countSpecificType(VideoType videoType) {

		if (videoType == null) {
			return null;
		} else {
			String sql = "select count(*) from video where video_type = \'"
					+ videoType + "\';";

			Integer count = getJdbcTemplate().queryForInt(sql);

			VideoReportingBean videoBean = new VideoReportingBean();
			videoBean.setCount(count);
			videoBean.setDescription(" videos of type " + videoType);

			return videoBean;
		}
	}

}
