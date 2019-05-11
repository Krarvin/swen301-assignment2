package test.nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import nz.ac.vuw.swen301.assignment2.MemAppender;

public class MemAppenderStressTest {
	@Test
	public void MemAppenderStressTest1() {
		long startTime = System.currentTimeMillis();
		long minute = 60000;
		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 10);
		Logger logger = Logger.getLogger("T2LayoutStressTest");
		logger.addAppender(appender);
		while(System.currentTimeMillis() - startTime < minute) {
		logger.error("Hello World");
		}
		System.out.println("Number of log events processed in 1 minute: " + (appender.getDiscardedLogCount() + appender.getCurrentLogs().size()));

	}
}
