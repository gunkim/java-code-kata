package io.github.gunkim.engine.storage.serializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonSerializer implements Serializer {
    private static final Gson gson = new Gson();

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public <T> T deserialize(String json) {
        return gson.fromJson(json, new TypeToken<T>() {
        }.getType());
    }
}
