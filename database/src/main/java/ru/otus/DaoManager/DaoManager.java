package ru.otus.DaoManager;

import ru.otus.Dao.AddressDataSetDao;
import ru.otus.Dao.DataSetDao;
import ru.otus.Dao.PhoneDataSetDao;
import ru.otus.Dao.UserDataSetDao;
import ru.otus.DataSet.DataSet;

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
