package io.github.gunkim;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private static final int DEFAULT_ARRAY_SIZE = 10;
    private static final int DEFAULT_ARRAY_EXPANSION_FACTOR = 2;
    private static final int INDEX_NOT_FOUND = -1;

    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        array = (T[]) new Object[DEFAULT_ARRAY_SIZE];
    }

    @Override
    public boolean add(T data) {
        expandArrayIfFull(size * DEFAULT_ARRAY_EXPANSION_FACTOR);
        addData(data);
        return true;
    }

    @Override
    public void add(int index, T element) {
        addAll(index, List.of(element));
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        addAll(size, collection);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        int requiredSize = (size + collection.size()) - (size - index);
        if (requiredSize >= array.length) {
            expandArrayIfFull(requiredSize * DEFAULT_ARRAY_EXPANSION_FACTOR);
        }

        System.arraycopy(array, index, array, index + collection.size(), collection.size());
        size += collection.size();

        var i = index;
        for (T element : collection) {
            array[i++] = element;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        var index = indexOf(o);
        if (index == INDEX_NOT_FOUND) {
            return false;
        }

        remove(index);

        return true;
    }


    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        var beforeValue = array[index];
        System.arraycopy(array, index + 1, array, index, size - index);
        return beforeValue;
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        return array[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        var beforeValue = array[index];
        array[index] = element;

        return beforeValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < array.length; i++) {
            var element = array[i];

            if (element.equals(o)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = array.length - 1; i >= 0; i--) {
            var element = array[i];

            if (element.equals(o)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != INDEX_NOT_FOUND;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        var arr = new Object[size];

        System.arraycopy(array, 0, arr, 0, size);

        return (T[]) arr;
    }

    @Override
    public ListIterator listIterator() {
        throw new RuntimeException("TODO");
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new RuntimeException("TODO");
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new RuntimeException("TODO");
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new RuntimeException("TODO");
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new RuntimeException("TODO");
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new RuntimeException("TODO");
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray(Object[] a) {
        return (T[]) new Object[0];
    }

    @Override
    public String toString() {
        return "MyArrayList{" +
                "array=" + Arrays.toString(array) +
                ", size=" + size +
                '}';
    }

    private void addData(T data) {
        array[size++] = data;
    }

    @SuppressWarnings("unchecked")
    private void expandArrayIfFull(int sizeUp) {
        if (array.length == size) {
            var originalArray = array;
            var newArray = new Object[sizeUp];

            System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);

            array = (T[]) newArray;
        }
    }

    private class MyIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[currentIndex++];
        }
    }
}
