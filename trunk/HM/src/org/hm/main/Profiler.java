package org.hm.main;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Profiler {
	static Logger log = Logger.getLogger("Profiler.class");
	private static HashMap<String, Long> msg2time = new HashMap<String, Long>();
	private static HashMap<String, Long> msg2deltaSum = new HashMap<String, Long>();
	
	public static void enter(String msg) {
		long time = System.currentTimeMillis();
		msg2time.put(msg, time);
	}	
	
	public static long leave(String msg) {
		long time = System.currentTimeMillis();
		Long oldTime = msg2time.get(msg);
		if (oldTime == null)
			return 0;
		long delta = time - oldTime;
		if (msg2deltaSum.get(msg) == null)
			msg2deltaSum.put(msg, 0L);
		msg2deltaSum.put(msg, msg2deltaSum.get(msg) + delta);
		return msg2deltaSum.get(msg);
	}
	
	public static void print(String msg, String variableMsg) {
		log.log(Level.INFO, msg + " " + variableMsg + (msg2deltaSum.get(msg) / 1000.0) + "s");		
	}
	
	public static void reset(String msg) {
		msg2deltaSum.put(msg, 0L);
	}
	
}
