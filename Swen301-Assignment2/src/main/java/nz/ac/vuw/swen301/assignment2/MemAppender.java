package nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.LogLog;

 import java.util.*;



public class MemAppender extends AppenderSkeleton
{
	private List<String> currentLogs;
	private long maxSize;
	private Layout layout;
	private long discardCount = 0;

	public MemAppender(Layout layout, long maxSize) {
		this.layout = layout;
		this.maxSize = maxSize;
		this.currentLogs = new ArrayList<String>();
	}

	public long getDiscardedLogCount() {
		return this.discardCount;
	}


	public void close() {
		// TODO Auto-generated method stub

	}

	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getCurrentLogs(){
		return this.currentLogs;
	}

	@Override
	protected void append(LoggingEvent event) {
		String s = layout.format(event);

		if(this.currentLogs.size() < this.maxSize) {
			this.currentLogs.add(s);
		}else if(currentLogs.size() == this.maxSize) {
			this.currentLogs.remove(0);
			this.discardCount++;
			this.currentLogs.add(s);
		}
	}

	public static void main(String[] args) {
	}
}


