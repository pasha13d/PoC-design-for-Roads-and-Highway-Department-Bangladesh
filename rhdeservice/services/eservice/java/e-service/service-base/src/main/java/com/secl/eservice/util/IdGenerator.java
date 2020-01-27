package com.secl.eservice.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.Synchronized;

@Component("idGenerator")
public class IdGenerator {

	private final int NUM_CHARS = 15;
	private String chars = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private String otpChars = "0123456789";
	private Random r = new Random();
	private SimpleDateFormat generalFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
	private final int TRACE_NUM_CHARS = 6;
	private final int OTP_NUM_CHARS = 6;
	private String traceChars = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	private SimpleDateFormat traceDateFormat = new SimpleDateFormat("yyMMdd");

	@Synchronized
	public String generateId() {
		Date today = new Date();
		String todayAsString = generalFormat.format(today);
		return todayAsString + "-" + getRandomWord();
	}

	@Synchronized
	public String uniqueId() {
		Date today = new Date();
		String todayAsString = generalFormat.format(today);
		return todayAsString + "-" + getRandomWord();
	}
	
	@Synchronized
	public String fileName() {
		Date today = new Date();
		String todayAsString = generalFormat.format(today);
		return todayAsString + "-" + getRandomWord();
	}

	@Synchronized
	public String generateTraceId() {
		Date today = new Date();
		String todayAsString = traceDateFormat.format(today);
		return todayAsString + getTraceRandomWord();
	}
	
	@Synchronized
	private String getRandomWord() {
		char[] buf = new char[NUM_CHARS];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = chars.charAt(r.nextInt(chars.length()));
		}
		return new String(buf);
	}

	@Synchronized
	public String getTraceRandomWord() {
		char[] buf = new char[TRACE_NUM_CHARS];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = traceChars.charAt(r.nextInt(traceChars.length()));
		}
		return new String(buf);
	}

	@Synchronized
	public String createOTP() {
		char[] buf = new char[OTP_NUM_CHARS];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = otpChars.charAt(r.nextInt(otpChars.length()));
		}
		return new String(buf);
	}
	
}
