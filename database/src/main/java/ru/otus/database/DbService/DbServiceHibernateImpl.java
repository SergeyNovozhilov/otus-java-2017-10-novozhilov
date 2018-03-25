package ru.otus.database.DbService;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.app.DBServiceMS;
import ru.otus.app.DataObject;
import ru.otus.cache.Cache;
import ru.otus.cache.CacheImpl;
import ru.otus.cache.Element;
import ru.otus.database.Dao.DataSetDao;
import ru.otus.database.DaoManager.DaoManager;
import ru.otus.database.DataSet.AddressDataSet;
import ru.otus.database.DataSet.DataSet;
import ru.otus.database.DataSet.PhoneDataSet;
import ru.otus.database.DataSet.UserDataSet;
import ru.otus.database.Executor.Executor;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.MessageSystem;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DbServiceHibernateImpl implements DbService, DBServiceMS {

    @Autowired
    private MessageSystem ms;

    private final SessionFactory sessionFactory;
    private final Cache<String, DataSet> cache;

    private final String NAME = "DbService";

    private final Address address = new Address();

    private final static Logger log = Logger.getLogger(DbServiceHibernateImpl.class.getName());

    public DbServiceHibernateImpl() {
        cache = new CacheImpl<>(5, 1000, 600, false);
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
        configuration.setProperty("hibernate.connection.username", "sa");
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

    @PostConstruct
    public void init(){
        if (ms != null) {
            ms.register(this);
        } else {
            log.log(Level.SEVERE, "Error: ms is null");
        }
        sebDb();
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public MessageSystem getMS() {
        return ms;
    }

    @Override
    public String getData() {
        System.out.println("!!! getData !!!");
        System.out.println("cache.getHitCount() " + cache.getHitCount());
        System.out.println("cache.getMissCount() " + cache.getMissCount());
        System.out.println("cache.getLifeTime() " + cache.getLifeTime());
        System.out.println("cache.getIdleTime() " + cache.getIdleTime());
        System.out.println("cache.getMax() " + cache.getMax());
        DataObject data = new DataObject(cache.getHitCount(), cache.getMissCount(), cache.getLifeTime(), cache.getIdleTime(), cache.getMax());
        return new Gson().toJson(data);
    }

    private void sebDb() {
        System.out.println("setDb");

        String street = "Ark street";
        AddressDataSet address = new AddressDataSet(street);
        UserDataSet user = new UserDataSet("Jones", 27, address, null);
        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet("110-12-23", user), new PhoneDataSet("113-23-34", user));
        user.setPhones(phones);
        save(user);
        UserDataSet userRead1 = load(1, UserDataSet.class);
        if (userRead1 == null) {
            System.out.println("Error! userRead1 is null");
        }
        UserDataSet userRead2 = load(1, UserDataSet.class);
        if (userRead2 == null) {
            System.out.println("Error! userRead2 is null");
        }
        try {
            Thread.sleep(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserDataSet userRead3 = load(1, UserDataSet.class);
        if (userRead3 == null) {
            System.out.println("Error! userRead3 is null");
        }
    }
}
