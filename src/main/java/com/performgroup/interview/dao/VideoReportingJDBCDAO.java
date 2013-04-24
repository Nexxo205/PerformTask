package com.performgroup.interview.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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

	// private DataSource dataSource;
	// private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

	public Collection<VideoReportingBean> countByDay() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<VideoReportingBean> resultBeans = new ArrayList<VideoReportingBean>();

		List<Map> listOfDates = getJdbcTemplate().queryForList(
				"select distinct(creation_date) from video;");

		for (Map distinctDate : listOfDates) {

			Collection<Timestamp> timestampValuesList = distinctDate.values();
			Timestamp value = timestampValuesList.iterator().next();
			
			value.setHours(0);
			value.setMinutes(0);
			value.setSeconds(0);
			value.setNanos(0);
			
			Timestamp valueNextDay = new Timestamp(value.getTime() + 24*60*60*1000);// + 1 day
			
			System.out.println("formatovany " + sdf.format(value));
			System.out.println("neformatovany "+value);
			System.out.println("neformatovany "+valueNextDay);
			// Date date = new Date(timestampValues.toArray()[0].);
			

			String sql = "select count(*) from video where creation_date between DATE\'"+sdf.format(value)+ "\' AND DATE\'"+sdf.format(valueNextDay)+"\';";
					//+ sdf.format(value) + "\';";

			System.out.println(sql);
			Integer count = getJdbcTemplate().queryForInt(sql);

			VideoReportingBean videoBean = new VideoReportingBean();
			videoBean.setCount(count);
			videoBean.setDescription("igested " + sdf.format(value));
			resultBeans.add(videoBean);

		}

		return resultBeans;
	}

	public Collection<VideoReportingBean> countByVideoType() {
		List<VideoReportingBean> resultBeans = new ArrayList<VideoReportingBean>();

		VideoType[] typevalues = VideoType.values();
				
		for (VideoType videoType : typevalues) {
			VideoReportingBean videoBean = countSpecificType(videoType);
			resultBeans.add(videoBean);
		}

		return resultBeans;
	}

	public VideoReportingBean countForVideoType(VideoType videoType) {
		VideoReportingBean videoBean = countSpecificType(videoType);
		return videoBean;
	}

	public VideoReportingBean countSpecificType(VideoType videoType) {
		String sql = "select count(*) from video where video_type = \'"
				+ videoType + "\';";
		
		Integer count = getJdbcTemplate().queryForInt(sql);

		VideoReportingBean videoBean = new VideoReportingBean();
		videoBean.setCount(count);
		videoBean.setDescription(" videos of type " + videoType);
		return videoBean;
	}

}
