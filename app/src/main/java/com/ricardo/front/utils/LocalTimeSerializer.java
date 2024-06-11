package com.ricardo.front.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer implements JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        return LocalTime.parse(je.getAsString(), formatter);
    }

    @Override
    public JsonElement serialize(LocalTime time, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(time.format(formatter));
    }
}
