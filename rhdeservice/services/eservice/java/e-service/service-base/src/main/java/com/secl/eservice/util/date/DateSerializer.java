package com.secl.eservice.util.date;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.secl.eservice.util.constant.Constant;

public class DateSerializer extends JsonSerializer<DateTime> {
	
	private DateTimeFormatter dateFormat = DateTimeFormat.forPattern(Constant.JAVA_DATE_FORMAT);

	@Override
	public void serialize(DateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if (date != null) {
			gen.writeString(dateFormat.print(date));
		}
	}
	
}