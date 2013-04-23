package com.performgroup.interview.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

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
import com.performgroup.interview.domain.VideoType;
import com.performgroup.interview.service.VideoService;

public class VideoProcessor {

	private transient VideoService videoService;

	private transient String videoIngestFolder;

	@Resource
	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
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
		if (in == null) {
			logger.info("Cannot find file");
		} else {

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
						
						String videoTitle = ""+ eElement.getElementsByTagName("title").item(0).getTextContent();
						VideoType videoType = null;
						String videoPath = ""+eElement.getElementsByTagName("path").item(0).getTextContent();

						for (VideoType vType : VideoType.values()) {
							
							System.out.println("element type "+eElement.getElementsByTagName("type").item(0).getTextContent());
							System.out.println("video type "+vType.toString());
							
							if (eElement.getElementsByTagName("type").item(0).getTextContent().equals(vType.toString())){
							videoType = vType;
														
							
						}else {
							//TODO handle better
							System.err.println("Wrong video type!");
						}
							

						}
						
						
						video.setTitle(videoTitle);
						video.setVideoType(videoType);
						video.setVideoPath(videoPath);
						video.setCreationDate(new Timestamp(new Date().getTime()));
						
						System.out.println(video.toString());
						videoService.addVideo(video);

					}

				}
				// TODO
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
