package ru.otus;


import ru.otus.DataSet.UserDataSet;
import ru.otus.DbConnection.PostgresConnection;
import ru.otus.DbService.DbServiceImpl;
import ru.otus.ResultMapper.UserMapper;

public class Main {
    public static void main(String[] args) {

        DbServiceImpl ds = new DbServiceImpl(new PostgresConnection());

        ds.register(UserDataSet.class, new UserMapper());

        ds.save(new UserDataSet("Jack", 23));

        UserDataSet user = ds.load(5, UserDataSet.class);

        if (user != null) {
            System.out.println("id = " + user.getId() + " name = " + user.getName() + " age = " + user.getAge());
        } else {
            System.out.println("null");
        }



        System.out.println("done");
    }

}
