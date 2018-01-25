package ru.otus.database.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressDataSet extends  DataSet{
    @Column(name = "street")
    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public AddressDataSet(long id, String street) {
        this.setId(id);
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
