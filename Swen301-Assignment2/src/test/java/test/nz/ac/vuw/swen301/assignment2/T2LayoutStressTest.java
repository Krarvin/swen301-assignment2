package test.nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.junit.Test;

import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.T2Layout;

public class T2LayoutStressTest {
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

		System.out.println("Number of log events processed in 1 minute: " + (appender.getDiscardedLogCount() + appender.getCurrentLogs().size()));

	}
}
