package ru.otus.person;

import java.util.List;
import java.util.Set;


import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Person {
    private String lastName;

    private PhoneNumber[] phoneNumbers;

    private String[] array;

    private List<String> strings;

    private Set<SomeClass> set;

    private Address address;

    private int age;

    private String firstName;





    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public PhoneNumber[] getPhoneNumbers ()
    {
        return phoneNumbers;
    }

    public void setPhoneNumber (PhoneNumber[] phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
    }

    public Address getAddress ()
    {
        return address;
    }

    public void setAddress (Address address)
    {
        this.address = address;
    }

    public int getAge ()
    {
        return age;
    }

    public void setAge (int age)
    {
        this.age = age;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public void setPhoneNumbers(PhoneNumber[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public Set<SomeClass> getSet() {
        return set;
    }

    public void setSet(Set<SomeClass> set) {
        this.set = set;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
}
