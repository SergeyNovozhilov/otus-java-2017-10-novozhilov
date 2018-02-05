package ru.otus.database.DbService;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.database.Dao.DataSetDao;
import ru.otus.database.DaoManager.DaoManager;
import ru.otus.database.DataSet.AddressDataSet;
import ru.otus.database.DataSet.DataSet;
import ru.otus.database.DataSet.PhoneDataSet;
import ru.otus.database.DataSet.UserDataSet;
import ru.otus.database.Executor.Executor;
import ru.otus.cache.Cache;
import ru.otus.cache.CacheImpl;
import ru.otus.cache.Element;

import java.util.List;

@Component
public class DbServiceHibernateImpl implements DbService {
    private final SessionFactory sessionFactory;
    private final Cache<String, DataSet> cache;

    public DbServiceHibernateImpl() {
        cache = new CacheImpl<>(5, 1000, 600, false);
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
        long id = exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(object.getClass());
            dao.setSession(session);
            return dao.save(object);
        });
        object.setId(id);
        cache.put(new Element<>(object.getClass().getName() + object.getId(), object));
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Element<String, DataSet> element = cache.get(clazz.getName() + id);
        if (element != null) {
                element.setAccessTime();
                return (T) element.getValue();
        }
        Executor exec = new Executor(sessionFactory);
        T data = (T) exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(clazz);
            dao.setSession(session);
            return dao.read(id);
        });

        cache.put(new Element<>(clazz.getName() + data.getId(), data));
        return data;
    }

    public <T extends DataSet> T read(String name, Class<T> clazz) {
        Executor exec = new Executor(sessionFactory);
        T data = (T) exec.execute(session -> {
            DataSetDao dao = DaoManager.getDao(clazz);
            dao.setSession(session);
            return dao.read(name);
        });
        cache.put(new Element<>(clazz.getName() + data.getId(), data));
        return data;
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
    public Cache getCache() {
        return cache;
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Closed");
        } else {
            System.out.println("SessionFactory is null.");
        }

        if (cache != null) {
            cache.dispose();
        }
    }
}
