package nz.ac.vuw.swen301.assignment2;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;

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
		this.closed = false;
	}

	public long getDiscardedLogCount() {
		if(this.closed) {
			throw new RuntimeException();
		}
		return this.discardCount;
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
		if(this.closed) {
			throw new RuntimeException();
		}
		String s = layout.format(event);

		if(this.currentLogs.size() < this.maxSize) {
			this.currentLogs.add(s);
		}else{
			this.currentLogs.remove(0);
			this.discardCount++;
			this.currentLogs.add(s);
		}
	}

}


