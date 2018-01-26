package ru.otus.cache;

import java.lang.ref.SoftReference;

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
