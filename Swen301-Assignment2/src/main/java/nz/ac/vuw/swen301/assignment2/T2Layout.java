package nz.ac.vuw.swen301.assignment2;
import freemarker.template.*;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class T2Layout extends Layout{

	public void activateOptions() {

	}

	private String pattern;

	public T2Layout(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String format(LoggingEvent loggingEvent){
		    try {
				Map<String, Object> input = new HashMap<String, Object>();
				  Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
				    String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(loggingEvent.getTimeStamp());
				    input.put("Category", loggingEvent.getLoggerName());
				    input.put("Date", timeStamp);
				    input.put("Message", loggingEvent.getMessage());
				    input.put("Priority", loggingEvent.getLevel());
		      Template t= new Template("T2Layout", new StringReader(this.pattern), cfg);
		      StringWriter stringWriter = new StringWriter();
		      t.process(input,stringWriter);
		      return stringWriter.toString();
		    } catch (IOException e) {
		      e.printStackTrace();
		    } catch (TemplateException e) {
		      e.printStackTrace();
		    }
		    return null;

	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}

}
