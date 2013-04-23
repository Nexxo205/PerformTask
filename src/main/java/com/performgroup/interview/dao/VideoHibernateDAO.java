package com.performgroup.interview.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.performgroup.interview.domain.Video;

public class VideoHibernateDAO extends HibernateDaoSupport implements VideoDAO {

	@SuppressWarnings("unchecked")
	public List<Video> findAll() {
		return getHibernateTemplate().loadAll(Video.class);
	}

	public Video findById(Integer videoId) {
		return (Video) getHibernateTemplate().load(Video.class, videoId);
	}

	public void delete(Video video) {
		getHibernateTemplate().delete(video);
	}

	public void save(Video video) {
		getHibernateTemplate().save(video);			
	}
}