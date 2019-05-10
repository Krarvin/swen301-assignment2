package nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MemAppenderTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	@Test
	public void test1() {
		Layout layout = new T1Layout();
		MemAppender appender = new MemAppender(layout, 1);
		Logger logger = Logger.getLogger("test2");
		logger.addAppender(appender);
		logger.error("testing test 2");
		logger.info("testing if max");
		assert (appender.getDiscardedLogCount() == 1);
		System.out.println(appender.getCurrentLogs().get(0).toString());
	}

	@Test
	public void test2() {
		Layout layout = new T2Layout();
		MemAppender appender = new MemAppender(layout, 1);
		Logger logger = Logger.getLogger("test2");
		logger.addAppender(appender);
		logger.info("testing test 2");
		logger.error("error test");
		System.out.println(appender.getCurrentLogs().get(0));
		assert (appender.getDiscardedLogCount() == 1);
	}





}
