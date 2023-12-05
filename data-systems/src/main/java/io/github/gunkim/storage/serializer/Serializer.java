package io.github.gunkim.storage.serializer;

public interface Serializer {
    String serialize(Object object);

    <T> T deserialize(String json);
}
