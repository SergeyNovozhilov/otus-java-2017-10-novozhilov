package ru.otus.DbService;

import ru.otus.DataSet.DataSet;
import ru.otus.cache.Cache;

import java.util.List;

public interface DbService extends AutoCloseable{
    <T extends DataSet> void save(T object);
    <T extends DataSet> T load(long id, Class<T> clazz);
    <T extends DataSet> T read(String name, Class<T> clazz);
    <T extends DataSet> List<T> readAll(Class<T> clazz);
    Cache getCache();
}
