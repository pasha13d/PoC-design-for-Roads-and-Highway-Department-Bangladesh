package com.secl.eservice.util.date;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.secl.eservice.util.constant.Constant;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

    private DateTimeFormatter dateFormat = DateTimeFormat.forPattern(Constant.DATE_FORMAT);

    @Override
    public DateTime deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException {
    	if(StringUtils.isBlank(paramJsonParser.getText())){
    		return null;
    	}
        return dateFormat.parseDateTime(paramJsonParser.getText().trim());
    }
    
}