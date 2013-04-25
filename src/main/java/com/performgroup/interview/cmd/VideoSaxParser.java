package com.performgroup.interview.cmd;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

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

	public Video parseXmlSax(InputStream in){
		 
	    try {
	 
	    	Video video = new Video();
	    	
	    
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		
	 
		DefaultHandler handler = new DefaultHandler() {
	 
		boolean title = false;
		boolean type = false;
		boolean path = false;
		
			 
		public void startElement(String uri, String localName,String qName, 
	                Attributes attributes) throws SAXException {
			System.out.println("Start Element :" + qName);
	 
			if (qName.equalsIgnoreCase("title")) {
				title = true;
			}
	 
			if (qName.equalsIgnoreCase("type")) {
				type = true;
			}
	 
			if (qName.equalsIgnoreCase("path")) {
				path = true;
			}			
	 
		}
	 
		public void endElement(String uri, String localName,
			String qName) throws SAXException {			
			System.out.println("End Element :" + qName);
		}
	 
		public void characters(char ch[], int start, int length) throws SAXException {
	 
			if (title) {
				System.out.println("title : " + new String(ch, start, length));
				titleValue = new String(ch, start, length);
				title = false;
			}
	 
			if (type) {
				
				for (VideoType vType : VideoType.values()) {

					if (new String(ch, start, length).equals(vType.toString())) {
						System.out.println("type : " + new String(ch, start, length));
						typeval = vType;
								type = false;
					}

				}
				
			}
	 
			if (path) {
				System.out.println("path : " + new String(ch, start, length));
				pathValue = new String(ch, start, length);
				path = false;
			}	 
			
		}
	 
	     };	     
	     
	       saxParser.parse(in, handler);
	       
	       if(typeval == null){
				return null;
			}
	       
	       video.setTitle(titleValue);
	       video.setVideoPath(pathValue);
	       video.setVideoType(typeval);
	       video.setCreationDate(new Timestamp(new Date().getTime()) );	    
	       
	      // video.setTags(tags);
	       	    
	       return video;
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
		return null;
	 
	   }
}
