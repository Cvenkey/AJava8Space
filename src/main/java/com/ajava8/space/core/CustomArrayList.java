package com.ajava8.space.core;

import java.util.ArrayList;

public class CustomArrayList<E> {
    private ArrayList<E> arrayList;
    private int capacity;

    public CustomArrayList(int initialCapacity) {
        this.arrayList = new ArrayList<>(initialCapacity);
        this.capacity = initialCapacity;
    }

    public boolean add(E e) {
        ensureCapacity();
        return arrayList.add(e);
    }

    public int size() {
        return arrayList.size();
    }

    public int getCapacity() {
        return capacity;
    }

    private void ensureCapacity() {
        // Increase capacity if needed
        if (arrayList.size() >= capacity) {
            capacity = capacity * 2; // Double the capacity
            ArrayList<E> newArrayList = new ArrayList<>(capacity);
            newArrayList.addAll(arrayList);
            arrayList = newArrayList;
        }
    }

    public E get(int index) {
        return arrayList.get(index);
    }

    public static void main(String[] args) {
        CustomArrayList<String> customList = new CustomArrayList<>(10);
        customList.add("A");
        customList.add("B");
        customList.add("C");

        System.out.println("Size: " + customList.size()); // Outputs: Size: 3
        System.out.println("Capacity: " + customList.getCapacity()); // Outputs: Capacity: 10
    }
}
