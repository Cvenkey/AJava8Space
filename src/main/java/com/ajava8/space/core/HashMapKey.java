package com.ajava8.space.core;


import java.util.Objects;

public final class HashMapKey {
    private String name;
    private long id;

    public HashMapKey() {
    }

    @Override
    public String toString() {
        return "HashMapKey{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public HashMapKey(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMapKey that = (HashMapKey) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public String getName() {
        return name;
    }

}
