package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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
 * Unit test for MemAppender Class.
 * @author hoongkevi
 *
 */
public class MemAppenderTest
{
    /*
     * test DiscardedLogCount method by adding 2 logs to an appender with maxSize 1 and checking discardedLogCount is 1.
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
	/*
     * test getCurrentLogs method by adding 2 logs to an appender with maxSize 2 and checking getCurrentLogs is 2.
     */
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
	/*
     * tests that both currentlogs and discardedLogCount work in correlation to each other
     */
	@Test
	public void MemAppenderTest3() {

		Layout layout = new PatternLayout();
		layout.activateOptions();
		layout.ignoresThrowable();
		MemAppender appender = new MemAppender(layout, 1);
		Logger logger = Logger.getLogger("MemAppenderTest3");
		logger.addAppender(appender);
		logger.error("error");
		logger.info("info");
		assertEquals(appender.getCurrentLogs().size(), 1);
		assertEquals(appender.getDiscardedLogCount(), 1);
	}
	/*
     * Test that getCurrentLogs returns an unmodifiable list.
     */
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
	/*
     * test that close truely closes the appender by calling a requiresLayout on appender after close and
     * expecting a RuntimeException.
     */
	@Test (expected = RuntimeException.class)
	public void closeTest() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("closeTest");
		appender.close();
		appender.requiresLayout();
	}
	/*
     * test that close truely closes the appender by calling GetCurrentLogs on appender after close and
     * expecting a RuntimeException.
     */
	@Test (expected = RuntimeException.class)
	public void closeTest2() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		appender.close();
		appender.getCurrentLogs();
	}
	/*
     * Tests that GetTop10Test will still work even if our maxSize is less than 10.
     */
	@Test
	public void GetTop10Test1() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		Logger logger = Logger.getLogger("Top10Test");
		logger.addAppender(appender);
		logger.error("error");
		logger.info("info");
		logger.fatal("fatal");
		logger.debug("debug");
		String[] topLogs = appender.getTop10Logs();
		assertEquals(topLogs[3], null);

	}
	/*
     * Tests that GetTop10Test works when maxSize is > 10.
     */
	@Test
	public void GetTop10Test2() throws ParseException {

		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 15);
		Logger logger = Logger.getLogger("Top10Test2");
		logger.addAppender(appender);
		for(int i =0; i<20; i++) {
			logger.error("error");
		}
		String[] topLogs = appender.getTop10Logs();
		assertNotNull(topLogs[9]);

	}

	/*
     * Tests that requiresLayout returns false if Layout is not found.
     */
	@Test
	public void NullLayoutTest() throws ParseException {
		MemAppender appender = new MemAppender(null, 3);
		assertEquals(false, appender.requiresLayout());
	}

	/*
     * Tests that requiresLayout returns true when there is a layout.
     */
	@Test
	public void LayoutTest() throws ParseException {
		Layout layout = new PatternLayout();
		MemAppender appender = new MemAppender(layout, 3);
		assertEquals(true, appender.requiresLayout());
	}

	/*
     * Tests Memappender with PatternLayout and level FATAL. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
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
	/*
     * Tests Memappender with level ERROR. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
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
	/*
     * Tests Memappender with level WARN. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
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

	/*
     * Tests Memappender with level INFO. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
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
	/*
     * Tests Memappender with level DEBUG. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
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
	/*
     * Tests Memappender with level TRACE. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
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
