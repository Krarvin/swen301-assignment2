package nz.ac.vuw.swen301.assignment2;
import java.io.StringWriter;

import org.apache.log4j.Category;
import org.apache.log4j.Layout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class T1Layout extends Layout{
	private Category cat;

	private Priority p;
	private Template t;

	public T1Layout() {

	}
	public void activateOptions() {


	}

	@Override
	public String format(LoggingEvent event) {
		VelocityEngine ve = new VelocityEngine();
		Template t = ve.getTemplate("template.vm");
		VelocityContext ce = new VelocityContext();
		ce.put("Category", event.getLoggerName());
		ce.put("Date", event.getTimeStamp());
		ce.put("Message", event.getMessage());
		ce.put("Priority", event.getLevel());
		StringWriter writer = new StringWriter();
		t.merge(ce, writer);
		return writer.toString();
//		Priority p = event.level;
//		cat = event.getLogger();
//
//		VelocityEngine ve = new VelocityEngine();
//		ve.init();
//		this.t = ve.getTemplate("src/main/java/nz/ac/vuw/swen301/assignment2/resources/template.vm");
//		VelocityContext ce = new VelocityContext();
//		ce.put(this.d, this.m);
//		StringWriter writer = new StringWriter();
//		this.t.merge(ce, writer);
//		return t.toString();
	}

	@Override
	public boolean ignoresThrowable() {

		return false;
	}

}