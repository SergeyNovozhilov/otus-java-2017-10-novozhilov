package ru.otus.DbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.Dao.DataSetDao;
import ru.otus.Dao.UserDataSetDao;
import ru.otus.DataSet.AddressDataSet;
import ru.otus.DataSet.DataSet;
import ru.otus.DataSet.PhoneDataSet;
import ru.otus.DataSet.UserDataSet;

public class DbServiceHibernateImpl implements DbService{
    private final SessionFactory sessionFactory;

    public DbServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5433/mydb");
        configuration.setProperty("hibernate.connection.username", "otus");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static SessionFactory createSessionFactory2(Configuration configuration) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return null;
    }


    @Override
    public <T extends DataSet> void save(T object) {
        try (Session session = sessionFactory.openSession()) {
            DataSetDao dao = getDao(object, session);
            dao.save(object);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }

    private <T extends DataSet> DataSetDao getDao(T object, Session session) {
        switch(object.getClass().getSimpleName()) {
            case "UserDataSet" :
                return new UserDataSetDao(session);
            default :
                return null;
        }
    }
}
