package org.dop.module.helper;

import com.nimbusds.jose.shaded.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

public class InstantTypeAdapter implements JsonDeserializer<Instant>, JsonSerializer<Instant> {
    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Instant.ofEpochMilli(json.getAsLong());
    }

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toEpochMilli());
    }
}
