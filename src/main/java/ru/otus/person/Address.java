package ru.otus.person;

public class Address {
    private String streetAddress;

    private int postalCode;

    private String state;

    private String city;

    public Address(String streetAddress, int postalCode, String state, String city) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
    }

    public String getStreetAddress ()
    {
        return streetAddress;
    }

    public void setStreetAddress (String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public int getPostalCode ()
    {
        return postalCode;
    }

    public void setPostalCode (int postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }
}
