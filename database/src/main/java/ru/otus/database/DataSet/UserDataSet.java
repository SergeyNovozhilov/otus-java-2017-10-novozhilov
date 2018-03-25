package ru.otus.database.DataSet;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet{
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PhoneDataSet> phones;

    public UserDataSet() {
    }

    public UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public UserDataSet(long id, String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        this.setId(id);
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
