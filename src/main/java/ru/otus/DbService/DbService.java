package ru.otus.DbService;

import ru.otus.DataSet.DataSet;
import ru.otus.ResultMapper.TResultMapper;

public interface DbService {
     <T extends DataSet> void save(T object);
     <T extends DataSet> T load(long id, Class<T> clazz);
     <T extends DataSet> void clearTable(Class<T> clazz);
}
