package ru.otus.Executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.otus.DataSet.DataSet;

import java.sql.SQLException;
import java.util.function.Function;

public class Executor {
    private final SessionFactory sessionFactory;

    public Executor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T execute(Function<Session, T> function) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
