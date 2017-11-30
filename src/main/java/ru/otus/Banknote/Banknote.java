package ru.otus.Banknote;

import java.util.List;

public interface Banknote {

    static Banknote fromValue() {
        return null;
    }

    static List<Integer> range(){
        return null;
    }

    int value();
}
