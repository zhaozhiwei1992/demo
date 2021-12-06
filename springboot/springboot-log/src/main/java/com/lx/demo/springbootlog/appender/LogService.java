package com.lx.demo.springbootlog.appender;

public class LogService {

	private static final ThreadLocal<String> runningJobids = new ThreadLocal<String>();

	public static String startJob() {
		return startJob("no path");
	}

	public static String startJob(String path) {
		String busjobid = System.currentTimeMillis() + "";
		runningJobids.set(busjobid);
		return busjobid;
	}

	public static String getJobid() {
		if (runningJobids.get() == null)
			return "nojobid";
		return runningJobids.get();
	}
}
