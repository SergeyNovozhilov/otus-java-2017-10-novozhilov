package ru.otus.Storage;

import ru.otus.ATM.Banknote;

import java.util.List;
import java.util.Map;

public interface Storage {
    void put (Banknote b);
    Map<Integer, List<Banknote>> get();
}
