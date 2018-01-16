package ru.otus.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

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

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    private TimerTask getTimerTask(final K key, Function<Element<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<Element<K, V>> ref = elements.get(key);
                boolean toRemove = false;
                if (ref != null) {
                    Element<K, V> element = ref.get();
                    if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                        toRemove = true;
                    }
                } else {
                    toRemove = true;
                }
                if (toRemove) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public Element<K, V> get(K key) {
        SoftReference<Element<K, V>> ref = elements.get(key);
        if (ref == null) {
            return null;
        }
        Element<K, V> element = ref.get();
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
