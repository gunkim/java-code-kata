package io.github.gunkim.engine.storage.lsm;

import java.io.IOException;

@FunctionalInterface
public interface IOOperation<T> {
    T run() throws IOException;
}
