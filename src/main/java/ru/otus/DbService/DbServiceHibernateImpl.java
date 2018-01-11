package ru.otus.DbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.Dao.DataSetDao;
import ru.otus.Dao.UserDataSetDao;
import ru.otus.DataSet.AddressDataSet;
import ru.otus.DataSet.DataSet;
import ru.otus.DataSet.PhoneDataSet;
import ru.otus.DataSet.UserDataSet;
import ru.otus.Executor.Executor;
import ru.otus.DaoManager.DaoManager;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class DbServiceHibernateImpl implements DbService {
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


    @Override
    public <T extends DataSet> void save(T object) {
        Executor exec = new Executor(sessionFactory);
        exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(object.getClass());
            dao.setSession(session);
            dao.save(object);
            return null;
        });
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Executor exec = new Executor(sessionFactory);
        return (T) exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(clazz);
            dao.setSession(session);
            return dao.read(id);
        });
    }

    public <T extends DataSet> T read(String name, Class<T> clazz) {
        Executor exec = new Executor(sessionFactory);
        return (T) exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(clazz);
            dao.setSession(session);
            return dao.read(name);
        });
    }

    public <T extends DataSet> List<T> readAll(Class<T> clazz) {
        Executor exec = new Executor(sessionFactory);
        return exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(clazz);
            dao.setSession(session);
            return dao.readAll();
        });
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Closed");
        } else {
            System.out.println("SessionFactory is null.");
        }
    }
}
