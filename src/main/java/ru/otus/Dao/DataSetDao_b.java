package ru.otus.Dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.DataSet.DataSet;
import ru.otus.DataSet.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class DataSetDao_b<T extends DataSet> {
    private Session session;
    private Class<T> persistentClass;

    public DataSetDao_b(Session session) {
        this.session = session;
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void save(T dataSet) {
        session.save(dataSet);
    }

    public T read(long id) {
        return session.load(getPersistentClass(), id);
    }

    public T readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getPersistentClass());
        Root<T> from = criteria.from(getPersistentClass());
        criteria.where(builder.equal(from.get("name"), name));
        Query<T> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<T> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getPersistentClass());
        criteria.from(getPersistentClass());
        return session.createQuery(criteria).list();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
}
