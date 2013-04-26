package com.performgroup.interview.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.performgroup.interview.domain.Video;

/**
 * Data Access Object to access video information 
 */

public interface VideoDAO {
	
	/**
	 * Find all videos in the system
	 * 
	 * @return a collection of <code>Video</code>
	 */
	Collection<Video> findAll();

	/**
	 * Returns a specific video
	 * 
	 * @param videoId
	 * @return a <code>Video</code> identified by the videoId
	 */
	Video findById(Integer videoId);
	
	/**
	 * Persists a <code>Video</code> object into the system
	 * 
	 * @param video the <code>Video</code> object to persist
	 */
	void save(Video video);
	
	/**
	 * Deletes a <code>Video</code> object from the system
	 * 
	 * @param video the <code>Video</code> object to delete
	 */
	void delete(Video video);

	Collection<Video> findByTitle(String title);
	
	Collection<Video> findByType(String stringType);
}