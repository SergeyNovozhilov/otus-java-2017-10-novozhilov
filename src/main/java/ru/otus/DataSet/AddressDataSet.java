package ru.otus.DataSet;

public class AddressDataSet extends  DataSet{
    private String street;

    public AddressDataSet(String street) {
        super(-1);
        this.street = street;
    }

    public AddressDataSet(long id, String street) {
        super(id);
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
