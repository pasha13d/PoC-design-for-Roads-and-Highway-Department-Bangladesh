package com.secl.eservice.util.constant;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.google.gson.GsonBuilder;
import com.secl.eservice.util.date.GsonBigDecimalUtil;
import com.secl.eservice.util.date.GsonDateTimeUtil;

import lombok.Synchronized;

public final class Constant {
	
	public static final String POSTGRES_DATE_TIME_FORMAT = "to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS.MS')::timestamp";
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
	public static final String JAVA_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String JAVA_DATE_FORMAT = "yyyy-MM-dd";
	public static final boolean PREETY_JSON = true;

	@Synchronized	
	public static ImmutablePair<String, String> getDateTime(DateTime now){
		if(now == null)
			now = new DateTime();
		String time = DateTimeFormat.forPattern(Constant.JAVA_DATE_TIME_FORMAT).print(now);
		return new ImmutablePair<String, String>(Constant.POSTGRES_DATE_TIME_FORMAT, time);
	}
	
	@Synchronized
	public static String getTime(long ms){
		DateTime now = null;
		if(ms <= 0){
			now = new DateTime();
		} else {
			now = new DateTime(ms);			
		}
		return DateTimeFormat.forPattern(Constant.DATE_FORMAT).print(now);
	}
	
	public static String print(Object object){
		GsonBuilder builder = new GsonBuilder()
			.registerTypeAdapter(BigDecimal.class, new GsonBigDecimalUtil())
			.registerTypeAdapter(DateTime.class, new GsonDateTimeUtil());
		builder.excludeFieldsWithoutExposeAnnotation();
		builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE, Modifier.FINAL);
		if(Constant.PREETY_JSON){
			builder.setPrettyPrinting();
		}
		return builder.create().toJson(object);
	}
	
	
}
