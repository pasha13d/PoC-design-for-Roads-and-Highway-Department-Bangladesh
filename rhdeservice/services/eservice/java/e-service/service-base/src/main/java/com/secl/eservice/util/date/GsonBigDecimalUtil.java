package com.secl.eservice.util.date;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonBigDecimalUtil implements JsonSerializer<BigDecimal>, JsonDeserializer<BigDecimal> {
	
	@Override
	public JsonElement serialize(BigDecimal src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == null) {
			return null;
		}
		return new JsonPrimitive(src.toPlainString());
		
	}

	@Override
	public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (StringUtils.isBlank(json.getAsString())) {
			return null;
		}
        return new BigDecimal(json.getAsString().trim());
	}
	
}