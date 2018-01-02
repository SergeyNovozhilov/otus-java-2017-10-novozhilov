package ru.otus.Dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.DataSet.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDao extends DataSetDao<UserDataSet> {

    public UserDataSetDao(Session session) {
        this.session = session;
    }

    @Override
    public void save(UserDataSet dataSet)  {
        session.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

    @Override
    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    @Override
    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }
}
