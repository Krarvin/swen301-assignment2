package test.nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.junit.Test;

import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.T2Layout;

public class T2LayoutStressTest {

	/*
	 * Stress tests T2Layout by calling loggingEvents for a minute and outputting the amount of
	 * discarded logs + current logs after 1 minute.
	 * @author hoongkevi
	 */
	@Test
	public void T2LayoutStressTest1() {
		long startTime = System.currentTimeMillis();
		long minute = 60000;
		Layout layout = new T2Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		MemAppender appender = new MemAppender(layout, 10);
		Logger logger = Logger.getLogger("T2LayoutStressTest");
		logger.addAppender(appender);
		while(System.currentTimeMillis() - startTime < minute) {
		logger.error("Hi");
		}
		System.out.println("FREEMARKER LAYOUT NUMBER OF LOG EVENTS PROCESSED IN 1 MINUTE: " + (appender.getDiscardedLogCount() + appender.getCurrentLogs().size()));


	}
}
