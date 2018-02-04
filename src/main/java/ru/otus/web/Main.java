package ru.otus.web;


import ru.otus.database.DataSet.AddressDataSet;
import ru.otus.database.DataSet.PhoneDataSet;
import ru.otus.database.DataSet.UserDataSet;
import ru.otus.database.DbService.DbService;
import ru.otus.database.DbService.DbServiceHibernateImpl;

import java.util.Arrays;
import java.util.List;

public class Main {

    private final static int PORT = 8092;
    private final static String PUBLIC = "public";
    public static void main(String[] args) throws Exception{

        DbService ds = new DbServiceHibernateImpl();

        setDb(ds);

        try {
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setDb(DbService ds) {
        System.out.println("setDb");

        String street = "Ark street";
        AddressDataSet address = new AddressDataSet(street);
        UserDataSet user = new UserDataSet("Jones", 27, address, null);
        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet("110-12-23", user), new PhoneDataSet("113-23-34", user));
        user.setPhones(phones);
        ds.save(user);
        UserDataSet userRead1 = ds.load(1, UserDataSet.class);
        if (userRead1 == null) {
            System.out.println("Error! userRead1 is null");
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserDataSet userRead2 = ds.load(1, UserDataSet.class);
        if (userRead2 == null) {
            System.out.println("Error! userRead2 is null");
        }
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserDataSet userRead3 = ds.load(1, UserDataSet.class);
        if (userRead3 == null) {
            System.out.println("Error! userRead3 is null");
        }
    }

}
