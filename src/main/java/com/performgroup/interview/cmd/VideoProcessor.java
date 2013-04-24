package com.performgroup.interview.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
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

	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	@Resource
	public void setVideoReportingService(
			VideoReportingService videoReportingService) {
		this.videoReportingService = videoReportingService;
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
	public void ingestVideo(Logger logger, String videoFile) {

		String path = videoIngestFolder + videoFile;

		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(path);

		System.out.println("path " + path);
		if (in == null) {
			logger.info("Cannot find file");
		} else {

			Video parsedVideo = new VideoSaxParser().parseXmlSax(in);
			//parseXmlDom(in);
			//videoService.addVideo(parsedVideo);


		}
	}

	private Video parseXmlDom(InputStream in) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("video");

			for (int currentNodePos = 0; currentNodePos < nodeList
					.getLength(); currentNodePos++) {

				Node currentNode = nodeList.item(currentNodePos);

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) currentNode;
					// title type path
					Video video = new Video();

					String videoTitle = ""
							+ eElement.getElementsByTagName("title")
									.item(0).getTextContent();
					VideoType videoType = null;
					String videoPath = ""
							+ eElement.getElementsByTagName("path").item(0)
									.getTextContent();
					// List<String> tags = new ArrayList<String>();
					//
					// NodeList keywordList =
					// eElement.getElementsByTagName("keywordSet");
					//
					// for (int currentKeyword = 0; currentKeyword <
					// keywordList.getLength(); currentKeyword++) {
					// Node currentKey= keywordList.item(currentKeyword);
					// if (currentKey.getNodeType() == Node.ELEMENT_NODE) {
					// Element keywordElement = (Element) currentKey;
					// tags.add(keywordElement.getElementsByTagName("keyword").item(0).getTextContent());
					// }
					//
					// }

					for (VideoType vType : VideoType.values()) {

						if (eElement.getElementsByTagName("type").item(0)
								.getTextContent().equals(vType.toString())) {
							videoType = vType;

						} else {
							// TODO handle better
							System.err.println("Wrong video type!");
						}

					}

					video.setTitle(videoTitle);
					video.setVideoType(videoType);
					video.setVideoPath(videoPath);
					video.setCreationDate(new Timestamp(new Date()
							.getTime()));

					System.out.println(video.toString());
					//return video;
					//videoService.add(video);
				}

			}
			// TODO
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return video;
	}

	public void countDates(Logger logger) {

		Collection<VideoReportingBean> videoReports = videoReportingService
				.countByDate();
		for (VideoReportingBean videoReportBean : videoReports) {
			videoReportOutput(logger, videoReportBean);
		}
	}

	public void countTypes(Logger logger) {

		Collection<VideoReportingBean> videoReports = videoReportingService
				.countByType();
		for (VideoReportingBean videoReportBean : videoReports) {
			videoReportOutput(logger, videoReportBean);
		}
	}

	public void countType(Logger logger, String videoType) {

		VideoReportingBean videoReport = videoReportingService
				.countForType(videoType);

		videoReportOutput(logger, videoReport);
	}

	private void videoReportOutput(Logger logger,
			VideoReportingBean videoReportBean) {

		String videoData = String.format("%d - %s ",
				videoReportBean.getCount(), videoReportBean.getDescription());
		logger.info(videoData);

	}

}
