package ru.otus.cache;

public interface Cache<K, V>{
    void put(Element<K, V> element);

    Element<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
