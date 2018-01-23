package ru.otus.Dao;

import org.hibernate.query.Query;
import ru.otus.DataSet.AddressDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AddressDataSetDao extends DataSetDao<AddressDataSet>{

    @Override
    public long save(AddressDataSet dataSet)  {
        return (long)getSession().save(dataSet);
    }

    @Override
    public AddressDataSet read(long id) {
        return getSession().load(AddressDataSet.class, id);
    }

    @Override
    public AddressDataSet read(String street) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<AddressDataSet> criteria = builder.createQuery(AddressDataSet.class);
        Root<AddressDataSet> from = criteria.from(AddressDataSet.class);
        criteria.where(builder.equal(from.get("street"), street));
        Query<AddressDataSet> query = getSession().createQuery(criteria);
        return query.uniqueResult();
    }

    @Override
    public List<AddressDataSet> readAll() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<AddressDataSet> criteria = builder.createQuery(AddressDataSet.class);
        criteria.from(AddressDataSet.class);
        return getSession().createQuery(criteria).list();
    }
}
