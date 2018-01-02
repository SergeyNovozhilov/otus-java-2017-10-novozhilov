package ru.otus.DbService;

import ru.otus.DataSet.DataSet;

public interface DbService {
    <T extends DataSet> void save(T object);
    <T extends DataSet> T load(long id, Class<T> clazz);
    default <T extends DataSet> void dropTable(T object){}
}
