package ru.otus.DbService;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.otus.DataSet.DataSet;
import ru.otus.Executor.Executor;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class DbServiceMyBatisImpl implements DbService {
    private SqlSessionFactory sqlSessionFactory;

    public DbServiceMyBatisImpl() {
        init();
    }

    public void init() {
        try {
            Reader reader = Resources.getResourceAsReader("config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> void save(T object) {
        Executor executor = new Executor(sqlSessionFactory);
        String space = object.getClass().getName() + ".insert";
        try {
            executor.execute(session -> {
                session.insert(space, object);
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Executor executor = new Executor(sqlSessionFactory);
        String space = clazz.getName() + ".getById";
        try {
            return (T)executor.execute(session -> {
                return session.selectOne(space, id);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends DataSet> void clearTable(Class<T> clazz) {

    }

}
