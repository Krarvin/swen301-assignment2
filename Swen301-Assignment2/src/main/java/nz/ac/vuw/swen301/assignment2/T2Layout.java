package nz.ac.vuw.swen301.assignment2;
import freemarker.template.*;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class T2Layout extends Layout{

	public void activateOptions() {
		// TODO Auto-generated method stub

	}

	public T2Layout() {

	}

	@Override
	public String format(LoggingEvent loggingEvent) {

		  Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

		    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		    cfg.setDefaultEncoding("UTF-8");
		    cfg.setLocale(Locale.US);
		    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		    Map <String, Object> input = new HashMap<String, Object>();

		    input.put("Category", loggingEvent.getLoggerName());

		    input.put("Date", loggingEvent.getTimeStamp());

		    input.put("Message", loggingEvent.getMessage());

		    input.put("Priority", loggingEvent.getLevel());
		    
		    try {
		      Template template = cfg.getTemplate("T2Layout.ftl");
		      StringWriter stringWriter = new StringWriter();
		      template.process(input,stringWriter);
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
		// TODO Auto-generated method stub
		return false;
	}

}
