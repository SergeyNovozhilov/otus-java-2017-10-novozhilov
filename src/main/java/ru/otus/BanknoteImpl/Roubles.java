package ru.otus.BanknoteImpl;

import ru.otus.Banknote.Banknote;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum Roubles implements Banknote{
    TEN(10), FIFTY(50), ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000);

    private final int value;

    Roubles(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static Roubles fromValue(int value) {
        for (Roubles r : Roubles.values()) {
            if (r.value == value) {
                return r;
            }
        }
        return null;
    }

    public static List<Integer> range() {
        List<Integer> list = Arrays.asList(Roubles.values()).stream().map(Roubles::value).collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }
}
