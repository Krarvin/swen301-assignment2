package nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;

import java.util.*;



public class MemAppender extends AppenderSkeleton implements MemAppenderMBean
{
	private List<String> currentLogs;
	private long maxSize;
	private Layout layout;
	private long discardCount = 0;

	public MemAppender(Layout layout, long maxSize) {
		this.layout = layout;
		this.maxSize = maxSize;
		this.currentLogs = new ArrayList<String>();
		this.closed = false;
	}


	public void close() {
		this.closed = true;
	}

	public boolean requiresLayout() {
		if(this.closed) {
			throw new RuntimeException();
		}
		if(this.layout != null) {
		return true;
		}
		return false;
	}

	public List<String> getCurrentLogs(){
		if(this.closed) {
			throw new RuntimeException();
		}
		return Collections.unmodifiableList(this.currentLogs);
	}

	@Override
	protected void append(LoggingEvent event) {
		String s = layout.format(event);

		if(this.currentLogs.size() < this.maxSize) {
			this.currentLogs.add(s);
		}else{
			this.currentLogs.remove(0);
			this.discardCount++;
			this.currentLogs.add(s);
		}

	}

	public String[] getTop10Logs() {
		String[] top10Logs = new String[10];
		if(this.getLogCount() < 10) {
			for(int i = 0; i < this.getLogCount();i++) {
				top10Logs[i] = this.getCurrentLogs().get(i);
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				top10Logs[i] = this.getCurrentLogs().get(this.getCurrentLogs().size() - (i + 1));
			}
		}
		return top10Logs;
	}

	public long getLogCount() {
		return this.currentLogs.size();
	}

	public long getDiscardedLogCount() {
		return this.discardCount;
	}

}


