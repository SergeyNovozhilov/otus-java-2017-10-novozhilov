package ru.otus.Storage;


import ru.otus.Banknote.Banknote;

import java.util.List;
import java.util.Map;

public interface Storage {
    void put (Banknote b);
    void put (List<Banknote> list);
    Map<Integer, List<Banknote>> get();
}
