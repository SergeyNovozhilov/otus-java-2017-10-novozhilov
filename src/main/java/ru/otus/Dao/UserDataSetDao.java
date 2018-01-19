package ru.otus.Dao;

import org.hibernate.query.Query;
import ru.otus.DataSet.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetDao extends DataSetDao<UserDataSet> {

    @Override
    public long save(UserDataSet dataSet)  {
        return (long)getSession().save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        return getSession().load(UserDataSet.class, id);
    }

    @Override
    public UserDataSet read(String name) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSet> query = getSession().createQuery(criteria);
        return query.uniqueResult();
    }

    @Override
    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return getSession().createQuery(criteria).list();
    }
}
