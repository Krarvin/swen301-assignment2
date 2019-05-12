package test.nz.ac.vuw.swen301.assignment2;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import nz.ac.vuw.swen301.assignment2.MemAppender;

public class MemAppenderStressTest {

//	@Test
//	public void MemAppenderStressTest1() {
//		long startTime = System.currentTimeMillis();
//		long minute = 60000;
//		Layout layout = new PatternLayout();
//		MemAppender appender = new MemAppender(layout, 10);
//		Logger logger = Logger.getLogger("T2LayoutStressTest");
//		logger.addAppender(appender);
//		while(System.currentTimeMillis() - startTime < minute) {
//		logger.error("Hello World");
//		}
//		System.out.println("Number of log events processed in 1 minute: " + (appender.getDiscardedLogCount() + appender.getCurrentLogs().size()));
//
//	}

	/*
	 * Stress tests MemAppender by calling loggingEvents for a minute and outputting the amount of
	 * discarded logs + current logs after 1 minute. This is run with an MBeanServer so that top 10 logs, count and currentlogs
	 * can be monitored.
	 */
	@Test
	public void MemAppenderMBeanStressTest1() throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("test.nz.ac.vuw.swen301.assignment2:type=MemAppenderStressTest");
		Layout layout = new PatternLayout();
		MemAppender mbean = new MemAppender(layout, 10);
		mbs.registerMBean(mbean, name);
		long startTime = System.currentTimeMillis();
		long minute = 60000;
		Logger logger = Logger.getLogger("T2LayoutStressTest");
		logger.addAppender(mbean);
		while(System.currentTimeMillis() - startTime < minute) {
		logger.error("Hello World");
		}
		System.out.println("MEMAPPENDER NUMBER OF LOG EVENTS PROCESSED IN 1 MINUTE: " + (mbean.getDiscardedLogCount() + mbean.getCurrentLogs().size()));


	}
}
