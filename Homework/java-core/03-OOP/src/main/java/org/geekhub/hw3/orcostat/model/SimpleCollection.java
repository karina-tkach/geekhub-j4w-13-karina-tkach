package org.geekhub.hw3.orcostat.model;

import java.util.Arrays;

public class SimpleCollection implements Collection {
    private Object[] collection;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public SimpleCollection() {
        collection = new Object[DEFAULT_CAPACITY];
    }

    public SimpleCollection(int capacity) {
        collection = new Object[capacity];
    }

    public SimpleCollection(Object... objects) {
        size = objects.length;
        int newLength = size * 2;
        collection = Arrays.copyOf(objects, newLength);

    }

    private void ensureCapacity() {
        int newLength = collection.length * 2;
        collection = Arrays.copyOf(collection, newLength);
    }

    @Override
    public void add(Object element) {
        if (element != null) {
            if (size == collection.length) {
                ensureCapacity();
            }
            collection[size++] = element;
        }
    }

    @Override
    public Object[] getElements() {
        return Arrays.copyOfRange(collection, 0, size);
    }

    @Override
    public int size() {
        return size;
    }
}
