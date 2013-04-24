package com.performgroup.interview.cmd;

import java.io.Console;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * The main entry point for the video ingest task
 */
public class VideoTaskRunner {

	private static transient final Logger LOGGER = Logger.getLogger(VideoTaskRunner.class);

	private static transient final String USAGE =
		"\n====== Usage ====== \n" +
		"list = list videos in the system \n" +
		"add = add a video after prompting for file path \n" +
		"bydate = count videos ingested at diferent dates \n" +
		"bytypes = count videos by all types \n" +
		"bytype = count videos of specified type \n" +
		"exit = exits the program \n" +
		"usage = prints this help";

	public static final void usage() {
		LOGGER.info(USAGE);
	}

	public static final void main(final String[] args) {

		Console c = System.console();

		if (c == null) {
			System.err.println("No console.");
			System.exit(1);
		}

		final GenericApplicationContext ctx = new GenericApplicationContext();
		final XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		String appContext = System.getProperty("appContext");
		xmlReader.loadBeanDefinitions(new ClassPathResource(appContext));
		ctx.refresh();

		VideoProcessor videoProcessor = (VideoProcessor) ctx.getBean("videoProcessor");
		//VideoReportingJDBCDAO videoReport = (VideoReportingJDBCDAO) ctx.getBean("videoReportingJDBCDAO");

		while (true) {
			
			System.out.print(">");
			
			String input = c.readLine();
			if (input.equalsIgnoreCase("usage")) {
				usage();
			}
			else if (input.equalsIgnoreCase("list")) {
				videoProcessor.listVideos(LOGGER);
			}
			else if (input.equalsIgnoreCase("add")) {
				System.out.println("Video file?");
				String videoDataFile = c.readLine();
				videoProcessor.ingestVideo(LOGGER, videoDataFile);
			}
			else if (input.equalsIgnoreCase("bydate")){
				videoProcessor.countDates(LOGGER);				
			}
			else if(input.equalsIgnoreCase("bytypes")){
				videoProcessor.countTypes(LOGGER);
			}
			else if(input.equalsIgnoreCase("bytype")){
				System.out.println("Video type?");
				String videoType = c.readLine();
				videoProcessor.countType(LOGGER, videoType);
			}
			else if (input.equalsIgnoreCase("exit")) {
				System.exit(0);
			}
			else {
				System.out.println("Unrecognised command");
			}

		}
	}
}
