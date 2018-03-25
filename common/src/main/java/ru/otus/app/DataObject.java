package ru.otus.app;

public class DataObject {
    private int hit;
    private int miss;
    private long lifeTime;
    private long idleTime;
    private int max;

    public DataObject(int hit, int miss, long lifeTime, long idleTime, int max) {
        this.hit = hit;
        this.miss = miss;
        this.lifeTime = lifeTime;
        this.idleTime = idleTime;
        this.max = max;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(long idleTime) {
        this.idleTime = idleTime;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
