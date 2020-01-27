package com.secl.eservice.util.date;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.secl.eservice.util.constant.Constant;

public class DateDeserializer extends JsonDeserializer<DateTime> {

	@Override
    public DateTime deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException {
    	if (StringUtils.isBlank(paramJsonParser.getText())) {
			return null;
		}
    	DateTime date = parseDate(paramJsonParser.getText().trim(), Constant.JAVA_DATE_FORMAT);
        if(date == null){
        	date = parseDate(paramJsonParser.getText().trim(), "dd-MM-yyyy");
        }
        return date;
    }
    
    private DateTime parseDate(String date, String format){
    	try {
    		return DateTimeFormat.forPattern(format).parseDateTime(date);
    	} catch (Exception e){
    		
    	}
    	return null;
    }
    
}