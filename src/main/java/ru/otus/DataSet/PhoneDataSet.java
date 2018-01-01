package ru.otus.DataSet;

public class PhoneDataSet extends  DataSet{
    private String number;

    public PhoneDataSet(String number) {
        super(-1);
        this.number = number;
    }

    public PhoneDataSet(long id, String number) {
        super(id);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
