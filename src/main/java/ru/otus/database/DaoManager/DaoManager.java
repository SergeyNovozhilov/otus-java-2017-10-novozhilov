package ru.otus.database.DaoManager;

import ru.otus.database.Dao.AddressDataSetDao;
import ru.otus.database.Dao.DataSetDao;
import ru.otus.database.Dao.PhoneDataSetDao;
import ru.otus.database.Dao.UserDataSetDao;
import ru.otus.database.DataSet.DataSet;

public class DaoManager {
    public static <T extends DataSet> DataSetDao getDao(Class<T> clazz) {
        switch(clazz.getSimpleName()) {
            case "UserDataSet" :
                return new UserDataSetDao();
            case "PhoneDataSet" :
                return new PhoneDataSetDao();
            case "AddressDataSet" :
                return new AddressDataSetDao();
            default :
                return null;
        }
    }
}
