//
// Copyright (c) 2013 figo GmbH
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package me.figo.internal;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
                return new JsonPrimitive(formatted);
            }
        };

		JsonDeserializer<Date> deserializer = new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null)
					return null;

				try {
					ZonedDateTime zdt = ZonedDateTime.parse(json.getAsString());
					Instant instant = zdt.toInstant();
					Date date = java.util.Date.from(instant);
					return date;
				} catch (DateTimeParseException e) {
					return null;
				}
			}
		};

		JsonDeserializer<BigDecimal> bigDecimalDeserializer = new JsonDeserializer<BigDecimal>() {
			@Override
			public BigDecimal deserialize(JsonElement json, Type type, JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null)
					return null;

				try {
					return new BigDecimal(json.getAsString());
				} catch (NumberFormatException nfe) {
					return null;
				}
			}
		};

        JsonSerializer<SetupAccountCredentials> setupAccountCredentialsSerializer = new JsonSerializer<SetupAccountCredentials>() {
            @Override
            public JsonElement serialize(SetupAccountCredentials setupAccountCredentials, Type type, JsonSerializationContext jsonSerializationContext) {
                if (setupAccountCredentials.getEncryptedCredentials() != null) {
                    JsonObject root = new JsonObject();
                    root.add("type", new JsonPrimitive("encrypted"));
                    root.add("value", new JsonPrimitive(setupAccountCredentials.getEncryptedCredentials()));
                    return root;
                }

                if (setupAccountCredentials.getCredentials() != null) {
                    JsonArray root = new JsonArray();
                    for(String credential: setupAccountCredentials.getCredentials()) {
                        root.add(credential);
                    }
                    return root;
                }

                return null;
            }
        };

		return new GsonBuilder().registerTypeAdapter(Date.class, serializer)
				.registerTypeAdapter(SetupAccountCredentials.class, setupAccountCredentialsSerializer)
				.registerTypeAdapter(Date.class, deserializer)
				.registerTypeAdapter(BigDecimal.class, bigDecimalDeserializer).excludeFieldsWithoutExposeAnnotation()
				.create();
    }
}
