package ru.otus.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressDataSet extends  DataSet{
    @Column(name = "street")
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
