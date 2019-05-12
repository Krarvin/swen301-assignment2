package test.nz.ac.vuw.swen301.assignment2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import freemarker.core.ParseException;
import nz.ac.vuw.swen301.assignment2.MemAppender;
import nz.ac.vuw.swen301.assignment2.T1Layout;

/*
 * Tests that all functionality of MemAppender works with a T1Layout and all logger levels.
 * Also tests that the formatting of logs from T1Layout is accurate.
 * @author hoongkevi
 */
public class T1LayoutTest {

	/*
     * test DiscardedLogCount method with Velocity Layout
     * by adding 2 logs to an appender with maxSize 1 and checking discardedLogCount is 1.
     */
	@Test
	public void T1LayoutTest1() {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		MemAppender appender = new MemAppender(layout, 1);
		Logger logger = Logger.getLogger("T1LayoutTest1");
		logger.addAppender(appender);
		logger.error("testing test 2");
		logger.info("testing if max");
		assertEquals(appender.getDiscardedLogCount(), 1);


	}

	/*
     * test getCurrentLogs method with Velocity Layout
     * by adding 2 logs to an appender with maxSize 2 and checking getCurrentLogs is 2.
     */
	@Test
	public void T1LayoutTest2() {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		MemAppender appender = new MemAppender(layout, 2);
		Logger logger = Logger.getLogger("T1LayoutTest2");
		logger.addAppender(appender);
		logger.error("testing test 2");
		logger.info("testing if max");
		assertEquals(appender.getCurrentLogs().size(), 2);
	}

	/*
     * tests that both currentlogs and discardedLogCount work in correlation to each other
     * with a velocity layout.
     */
	@Test
	public void T1LayoutTest3() {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		layout.activateOptions();
		layout.ignoresThrowable();
		MemAppender appender = new MemAppender(layout, 2);
		Logger logger = Logger.getLogger("T1LayoutTest3");
		logger.addAppender(appender);
		logger.error("testing test 2");
		logger.info("testing if max");
		assertEquals(appender.getCurrentLogs().size(), 2);

	}

	/*
     * tests that the String Log output by velocity engine matches the Layout we made when running
     * T1 Layout format.
     */
	@Test
	public void T1FormatTest() {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Message - ${Message}");
		layout.activateOptions();
		layout.ignoresThrowable();
		MemAppender appender = new MemAppender(layout, 2);
		Logger logger = Logger.getLogger("FormatTest");
		logger.addAppender(appender);
		logger.info("testing Format");
		assertEquals(appender.getCurrentLogs().size(), 1);
		assertEquals(appender.getCurrentLogs().get(0), ("Priority - INFO , Category - FormatTest, Message - testing Format"));

	}
	/*
     * test that close truely closes the appender by calling a getCurrentLogs on appender after close and
     * expecting a RuntimeException.
     */
	@Test (expected = RuntimeException.class)
	public void closeTest() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		MemAppender appender = new MemAppender(layout, 3);
		appender.close();
		appender.getCurrentLogs();

	}

	/*
     * Tests that requiresLayout returns true for a T1Layout.
     */
	@Test
	public void LayoutTest() throws ParseException {
		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
		MemAppender appender = new MemAppender(layout, 3);
		assertEquals(true, appender.requiresLayout());
	}

	/*
     * Tests Memappender with T1Layout and level FATAL. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
	@Test
	public void testFatalLevel() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
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
     * Tests Memappender with T1Layout and level ERROR. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
	@Test
	public void testErrorLevel() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
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
     * Tests Memappender with T1Layout and level WARN. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
	@Test
	public void testWarnLevel() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
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
     * Tests Memappender with T1Layout and level INFO. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
	@Test
	public void testInfoLevel() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
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
     * Tests Memappender with T1Layout and level DEBUG. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
	@Test
	public void testDebugLevel() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
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
     * Tests Memappender with T1Layout and level TRACE. So that currentLogs only stores Levels that are above.
     * And discards levels that are below.
     */
	@Test
	public void testTraceLevel() throws ParseException {

		Layout layout = new T1Layout("Priority - ${Priority} , Category - ${Category}, Date - ${Date}, Message - ${Message}");
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
