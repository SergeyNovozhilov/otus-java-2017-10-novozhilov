package ru.otus.database.Dao;

import org.hibernate.Session;
import ru.otus.database.DataSet.DataSet;

import java.util.List;

public abstract class DataSetDao <T extends DataSet> {
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public abstract long save(T dataSet);

    public abstract T read(long id);

    public abstract T read(String name);

    public abstract List<T> readAll();
}
