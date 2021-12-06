package com.lx.demo.springbootlog.appender;

public class BusRunTimeLogBO implements Comparable<BusRunTimeLogBO> {

	private String jobid;

	private long time;

	private String level;

	private String message;

	private String clazz;

	public static final String serverid = System.nanoTime() + "";

	public BusRunTimeLogBO(String jobid, long time, String level, String message, String clazz) {
		super();
		this.jobid = jobid;
		this.time = time;
		this.level = level;
		this.message = message;
		this.clazz = clazz;
	}

	@Override
	public int compareTo(BusRunTimeLogBO o) {

		return (time - o.time) > 0 ? 1 : -1;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public static String getServerid() {
		return serverid;
	}

}
