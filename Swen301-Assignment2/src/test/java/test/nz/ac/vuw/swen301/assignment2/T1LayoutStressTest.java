package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import freemarker.core.ParseException;
import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.T1Layout;

public class T1LayoutStressTest {
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

		System.out.println("Number of log events processed in 1 minute: " + (appender.getDiscardedLogCount() + appender.getCurrentLogs().size()));

	}
}
