package me.figo.internal;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonAdapter {
	public static Gson createGson() {
		JsonSerializer<Date> serializer = new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type type, JsonSerializationContext context) {
				if (src == null)
					return null;
				
				String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(src);
				return new JsonPrimitive(formatted.substring(0, 22) + ":" + formatted.substring(22));
			}
		};
		
		JsonDeserializer<Date> deserializer = new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
				if (json == null)
					return null;
				
				String s = json.getAsString().replace("Z", "+0000");
				try {
					return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ").parse(s);
				} catch (ParseException e) {
					return null;
				}
			}
		};
		
		return new GsonBuilder()
			.registerTypeAdapter(Date.class, serializer)
			.registerTypeAdapter(Date.class, deserializer)
			.create();
	}
}
