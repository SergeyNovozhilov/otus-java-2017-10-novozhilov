package ru.otus.Dao;

import org.hibernate.Session;
import ru.otus.DataSet.DataSet;

import java.util.List;

public abstract class DataSetDao <T extends DataSet> {
    protected Session session;

    public abstract void save(T dataSet);

    public abstract T read(long id);

    public abstract T readByName(String name);

    public abstract List<T> readAll();
}
