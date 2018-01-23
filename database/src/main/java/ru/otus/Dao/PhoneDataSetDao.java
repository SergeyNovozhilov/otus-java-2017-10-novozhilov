package ru.otus.Dao;

import org.hibernate.query.Query;
import ru.otus.DataSet.PhoneDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PhoneDataSetDao extends  DataSetDao<PhoneDataSet> {

    @Override
    public long save(PhoneDataSet dataSet)  {
        return (long)getSession().save(dataSet);
    }

    @Override
    public PhoneDataSet read(long id) {
        return getSession().load(PhoneDataSet.class, id);
    }

    @Override
    public PhoneDataSet read(String number) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<PhoneDataSet> criteria = builder.createQuery(PhoneDataSet.class);
        Root<PhoneDataSet> from = criteria.from(PhoneDataSet.class);
        criteria.where(builder.equal(from.get("number"), number));
        Query<PhoneDataSet> query = getSession().createQuery(criteria);
        return query.uniqueResult();
    }

    @Override
    public List<PhoneDataSet> readAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<PhoneDataSet> criteria = builder.createQuery(PhoneDataSet.class);
        criteria.from(PhoneDataSet.class);
        return getSession().createQuery(criteria).list();
    }
}
