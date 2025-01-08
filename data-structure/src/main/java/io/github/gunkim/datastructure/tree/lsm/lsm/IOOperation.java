package io.github.gunkim.datastructure.tree.lsm.lsm;

import java.io.IOException;

@FunctionalInterface
public interface IOOperation<T> {
    T run() throws IOException;
}
