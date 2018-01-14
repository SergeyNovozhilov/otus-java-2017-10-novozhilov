package ru.otus.cache;

import java.lang.ref.SoftReference;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CacheImpl<K, V> implements Cache<K, V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<Element<K, V>>> elements = new HashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(Element<K, V> element) {
        if (elements.size() == maxElements) {
            removeElement();
        }

        K key = element.getKey();
        elements.put(key, new SoftReference<>(element));
    }

    @Override
    public Element<K, V> get(K key) {
        Element<K, V> element = elements.get(key).get();
        if (element != null) {
            hit++;
            element.setAccessTime();
        } else {
            miss++;
        }
        return element;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private void removeElement() {
        SoftReference<Element<K, V>> min = elements.values().stream().min(Comparator.comparing(e -> e.get().getLastAccessTime())).orElse(null);
        if (min != null) {
            elements.remove(min.get().getKey());
        }
    }
}
