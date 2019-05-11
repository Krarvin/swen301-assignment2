package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Event;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

import freemarker.core.ParseException;
import nz.ac.vuw.swen301.assignment2.MemAppender;

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
	public void MemAppenderTest1() {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 1);
		Logger logger = Logger.getLogger("MemAppenderTest1");
		logger.addAppender(appender);
		logger.error("error");
		logger.info("info");
		assertEquals(appender.getDiscardedLogCount(), 1);

	}

	@Test
	public void MemAppenderTest2() {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 2);
		Logger logger = Logger.getLogger("MemAppenderTest2");
		logger.addAppender(appender);
		logger.error("error");
		logger.info("info");
		assertEquals(appender.getCurrentLogs().size(), 2);
	}

	@Test
	public void MemAppenderTest3() {

		Layout layout = new PatternLayout();
		layout.activateOptions();
		layout.ignoresThrowable();
		MemAppender appender = new MemAppender(layout, 2);
		Logger logger = Logger.getLogger("MemAppenderTest3");
		logger.addAppender(appender);
		logger.error("error");
		logger.info("info");
		assertEquals(appender.getCurrentLogs().size(), 2);
	}

	@Test (expected = UnsupportedOperationException.class)
	public void UnmodifiableListTest() {
		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 2);
		Logger logger = Logger.getLogger("UnmodifiableTest");
		logger.addAppender(appender);
		logger.error("error");
		logger.error("error");
		appender.getCurrentLogs().add(1, "should throw exception");
	}

	@Test (expected = RuntimeException.class)
	public void closeTest() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("closeTest");
		logger.addAppender(appender);
		logger.error("error");
		logger.info("info");
		logger.fatal("fatal");
		logger.debug("debug");
		assertEquals(appender.getCurrentLogs().size(), 3);
		assertEquals(appender.getDiscardedLogCount(), 1);
		appender.close();
		appender.getCurrentLogs();

	}

	@Test
	public void NullLayoutTest() throws ParseException {
		MemAppender appender = new MemAppender(null, 3);
		assertEquals(false, appender.requiresLayout());
	}

	@Test
	public void LayoutTest() throws ParseException {
		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		assertEquals(true, appender.requiresLayout());
	}

	@Test
	public void testFatalLevel() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("fatal test");
		logger.setLevel(Level.FATAL);
		logger.addAppender(appender);
		logger.error("error level");
		logger.info("info level");
		logger.fatal("fatal level");
		logger.debug("debug level");
		logger.warn("warn level");
		logger.trace("trace level");
		assertEquals(1, appender.getCurrentLogs().size());
		assertEquals(0, appender.getDiscardedLogCount());
	}

	@Test
	public void testErrorLevel() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("Error Test");
		logger.setLevel(Level.ERROR);
		logger.addAppender(appender);
		logger.error("error level");
		logger.info("info level");
		logger.fatal("fatal level");
		logger.debug("debug level");
		logger.warn("warn level");
		logger.trace("trace level");
		assertEquals(2, appender.getCurrentLogs().size());
		assertEquals(0, appender.getDiscardedLogCount());

	}

	@Test
	public void testWarnLevel() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("Warn Test");
		logger.setLevel(Level.WARN);
		logger.addAppender(appender);
		logger.error("error level");
		logger.info("info level");
		logger.fatal("fatal level");
		logger.debug("debug level");
		logger.warn("warn level");
		logger.trace("trace level");
		assertEquals(3, appender.getCurrentLogs().size());
		assertEquals(0, appender.getDiscardedLogCount());

	}


	@Test
	public void testInfoLevel() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("Info Test");
		logger.setLevel(Level.INFO);
		logger.addAppender(appender);
		logger.error("error level");
		logger.info("info level");
		logger.fatal("fatal level");
		logger.debug("debug level");
		String copy = appender.getCurrentLogs().get(0);
		logger.warn("warn level");
		logger.trace("trace level");
		assertEquals(3, appender.getCurrentLogs().size());
		assertNotEquals(copy, appender.getCurrentLogs().get(0));
		assertEquals(1, appender.getDiscardedLogCount());

	}

	@Test
	public void testDebugLevel() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("Debug test");
		logger.setLevel(Level.DEBUG);
		logger.addAppender(appender);
		logger.error("error level");
		logger.info("info level");
		logger.fatal("fatal level");
		String copy1 = appender.getCurrentLogs().get(0);
		String copy2 = appender.getCurrentLogs().get(1);
		logger.debug("debug level");
		logger.warn("warn level");
		logger.trace("trace level");
		assertEquals(3, appender.getCurrentLogs().size());
		assertNotEquals(copy1, appender.getCurrentLogs().get(0));
		assertNotEquals(copy2, appender.getCurrentLogs().get(1));
		assertEquals(2, appender.getDiscardedLogCount());

	}

	@Test
	public void testTraceLevel() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("Trace test");
		logger.setLevel(Level.TRACE);
		logger.addAppender(appender);
		logger.error("error level");
		logger.info("info level");
		logger.fatal("fatal level");
		String copy1 = appender.getCurrentLogs().get(0);
		String copy2 = appender.getCurrentLogs().get(1);
		String copy3 = appender.getCurrentLogs().get(2);
		logger.debug("debug level");
		logger.warn("warn level");
		logger.trace("trace level");
		assertEquals(appender.getCurrentLogs().size(), 3);
		assertNotEquals(copy1, appender.getCurrentLogs().get(0));
		assertNotEquals(copy2, appender.getCurrentLogs().get(1));
		assertNotEquals(copy3, appender.getCurrentLogs().get(2));
		assertEquals(3, appender.getDiscardedLogCount());

	}




}
