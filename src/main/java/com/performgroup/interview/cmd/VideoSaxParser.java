package com.performgroup.interview.cmd;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.performgroup.interview.domain.Video;

public class VideoSaxParser {

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
				System.out.println("type : " + new String(ch, start, length));
				String typevalString
				type = false;
			}
	 
			if (path) {
				System.out.println("path : " + new String(ch, start, length));
				String pathValue = new String(ch, start, length);
				path = false;
			}	 
			
		}
	 
	     };
	 
	       saxParser.parse(in, handler);
	       video.set
	       System.out.println(video.toString());
	 
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
		return null;
	 
	   }
}
