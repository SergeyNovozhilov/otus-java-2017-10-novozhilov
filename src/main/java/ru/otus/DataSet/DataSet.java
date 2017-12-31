package ru.otus.DataSet;

public abstract class DataSet {
    protected long id;

    public DataSet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
