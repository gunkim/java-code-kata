package io.github.gunkim.algorithm.queue;

import java.util.Stack;

public class TwoStackQueue<T> {
    private final Stack<T> inStack = new Stack<>();
    private final Stack<T> outStack = new Stack<>();

    public void add(T value) {
        inStack.push(value);
    }

    public T get() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                T value = inStack.pop();
                outStack.push(value);
            }
        }
        return outStack.pop();
    }
}
