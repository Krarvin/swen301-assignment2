package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

import freemarker.core.ParseException;
import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.T1Layout;

public class T1LayoutStressTest {

	/*
	 * Stress tests T2Layout by calling loggingEvents for a minute and outputting the amount of
	 * discarded logs + current logs after 1 minute.
	 * @author hoongkevi
	 */
	@Test
	public void T1LayoutStressTest1() {
		long startTime = System.currentTimeMillis();
		long minute = 60000;
		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		MemAppender appender = new MemAppender(layout, 10);
		Logger logger = Logger.getLogger("T1LayoutStressTest1");
		logger.addAppender(appender);
		while(System.currentTimeMillis() - startTime < minute) {
		logger.error("Hi");
		}
		System.out.println("VELOCITY NUMBER OF LOG EVENTS PROCESSED IN 1 MINUTE: " + (appender.getDiscardedLogCount() + appender.getCurrentLogs().size()));

	}
}
