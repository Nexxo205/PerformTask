package com.performgroup.interview.cmd;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.performgroup.interview.domain.Video;
import com.performgroup.interview.domain.VideoType;


public class VideoSaxParser {

	private String titleValue;
	private String pathValue;
	private VideoType typeval;
	private List<String> tags = new ArrayList<String>();

	
	/**
	 * This method parses xml file and creates entity of Video with values 
	 * from parsed xml file
	 * 
	 * @param in - inputStream of xml file
	 * @return video entity
	 */
	public Video parseXmlSax(InputStream in) {

		try {

			Video video = new Video();

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean title = false;
				boolean type = false;
				boolean path = false;
				boolean keyword = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {	

					if (qName.equalsIgnoreCase("title")) {
						title = true;
					}
					if (qName.equalsIgnoreCase("type")) {
						type = true;
					}
					if (qName.equalsIgnoreCase("path")) {
						path = true;
					}
					if (qName.equalsIgnoreCase("keyword")) {
						keyword = true;
					}

				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {

					if (title) {						
						titleValue = new String(ch, start, length);
						title = false;
					}

					if (type) {
						// searching through existing values for value specified
						for (VideoType vType : VideoType.values()) { 
							if (new String(ch, start, length).equals(vType
									.toString())) {

								typeval = vType;
								type = false;
							}
						}
					}

					if (path) {
						pathValue = new String(ch, start, length);
						path = false;
					}

					if (keyword) {
						tags.add(new String(ch, start, length));
						keyword = false;
					}

				}

			};

			saxParser.parse(in, handler);

			if (typeval == null) {
				return null;
			}

			video.setTitle(titleValue);
			video.setVideoPath(pathValue);
			video.setVideoType(typeval);
			video.setCreationDate(new Timestamp(new Date().getTime()));

			// video.setTags(tags);

			return video;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
