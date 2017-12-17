package ru.otus.person;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SomeClass {
    private String[] array;

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
}
