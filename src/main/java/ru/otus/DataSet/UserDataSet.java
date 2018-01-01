package ru.otus.DataSet;

import java.util.List;

public class UserDataSet extends DataSet{
    private String name;
    private int age;
    private AddressDataSet address;
    private List<PhoneDataSet> phones;

    public UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        super(-1);
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public UserDataSet(long id, String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        super(id);
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }
}
