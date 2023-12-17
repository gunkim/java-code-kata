package io.github.gunkim.engine.storage.serializer;

public interface Serializer {
    String serialize(Object object);

    <T> T deserialize(String json);
}
