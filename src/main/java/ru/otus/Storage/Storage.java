package ru.otus.Storage;


import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;

import java.util.List;

public interface Storage {
    void put(Banknote b);

    void put(List<Banknote> list);

    int getAmount(Banknote b);

    List<Banknote> get(Banknote b, int amount) throws AtmException;

    List<Banknote> getAll();

    List<Banknote> range();

    boolean initStorageMemory(List<Banknote> list, int initial);

    void save();

    void restore();

    void restoreInitial();
}
