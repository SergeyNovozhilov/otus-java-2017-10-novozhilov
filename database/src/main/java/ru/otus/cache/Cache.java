package ru.otus.cache;

public interface Cache<K, V>{
    void put(Element<K, V> element);

    Element<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    long getLifeTime();

    long getIdleTime();

    int getMax();

    void dispose();
}
