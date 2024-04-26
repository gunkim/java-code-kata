package io.github.gunkim.datasystems.engine.storage.serializer;

public interface Serializer {
    String serialize(Object object);

    <T> T deserialize(String json);
}
