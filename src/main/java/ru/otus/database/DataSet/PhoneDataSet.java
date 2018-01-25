package ru.otus.database.DataSet;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends  DataSet{
    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name="users_id", nullable=false)
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, UserDataSet user) {
        this.number = number;
        this.user = user;
    }

    public PhoneDataSet(long id, String number, UserDataSet user) {
        this.setId(id);
        this.number = number;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
