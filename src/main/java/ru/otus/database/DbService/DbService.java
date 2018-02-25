package ru.otus.database.DbService;

import ru.otus.cache.Cache;
import ru.otus.database.DataSet.DataSet;
import ru.otus.messageSystem.Addressee;

import java.util.List;

public interface DbService extends AutoCloseable, Addressee{
    <T extends DataSet> void save(T object);
    <T extends DataSet> T load(long id, Class<T> clazz);
    <T extends DataSet> T read(String name, Class<T> clazz);
    <T extends DataSet> List<T> readAll(Class<T> clazz);
    Cache getCache();
}
