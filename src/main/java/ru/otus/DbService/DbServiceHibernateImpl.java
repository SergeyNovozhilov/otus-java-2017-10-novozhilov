package ru.otus.DbService;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.Dao.DataSetDao;
import ru.otus.DataSet.AddressDataSet;
import ru.otus.DataSet.DataSet;
import ru.otus.DataSet.PhoneDataSet;
import ru.otus.DataSet.UserDataSet;
import ru.otus.Executor.Executor;
import ru.otus.Utils.DaoManager;

import java.sql.SQLException;

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



    @Override
    public <T extends DataSet> void save(T object) {
        Executor exec = new Executor(sessionFactory);
        try {
            exec.execute(session -> {
                DataSetDao dao = DaoManager.getDao(object.getClass());
                dao.setSession(session);
                dao.save(object);
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Executor exec = new Executor(sessionFactory);
        try {
            return (T)exec.execute(session -> {
                DataSetDao dao = DaoManager.getDao(clazz);
                dao.setSession(session);
                return dao.read(id);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }
}
