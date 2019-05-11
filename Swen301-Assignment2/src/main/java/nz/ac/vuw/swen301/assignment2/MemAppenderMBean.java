package nz.ac.vuw.swen301.assignment2;


public interface MemAppenderMBean {
	public String[] getTop10Logs();
	public long getLogCount();
	public long getDiscardedLogCount();
}