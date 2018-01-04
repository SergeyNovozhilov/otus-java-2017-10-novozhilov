package ru.otus.Executor;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import ru.otus.DataSet.DataSet;

import java.sql.SQLException;
import java.util.function.Function;

public class Executor {
    private final SqlSessionFactory sessionFactory;

    public Executor(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T extends DataSet> T execute(Function<SqlSession, T> function) throws SQLException {
        try (SqlSession session = this.sessionFactory.openSession()) {
            T result = function.apply(session);
            session.commit();
            return result;
        }
    }

}
