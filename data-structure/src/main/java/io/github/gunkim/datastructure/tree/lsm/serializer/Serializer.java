package io.github.gunkim.datastructure.tree.lsm.serializer;

public interface Serializer {
    String serialize(Object object);

    <T> T deserialize(String json);
}
