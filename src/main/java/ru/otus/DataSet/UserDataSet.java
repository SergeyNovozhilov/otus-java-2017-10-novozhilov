package ru.otus.DataSet;

public class UserDataSet extends DataSet{
    private String name;
    private int age;

    public UserDataSet(String name, int age) {
        super(-1);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
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
}
