package com.performgroup.interview.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoType;

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
	
	public Collection<Video> findByTitle(String title){	
		DetachedCriteria criteria = DetachedCriteria.forClass(Video.class).add(Restrictions.eq("title", title));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Collection<Video> findByType(String stringType){
		VideoType type = null;
		for (VideoType t : VideoType.values()) {
			if(t.toString().equals(stringType.toUpperCase())) {
				type=t;
				System.out.println("type found");
			}else System.out.println("type not found");
				
			
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Video.class).add(Restrictions.eq("videoType", type));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Collection<Video> findByTag(String tag){	
		DetachedCriteria criteria = DetachedCriteria.forClass(Video.class).add(Restrictions.eq("tags", tag));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	
}