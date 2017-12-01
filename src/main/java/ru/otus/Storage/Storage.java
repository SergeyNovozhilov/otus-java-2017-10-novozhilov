package ru.otus.Storage;


import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;

import java.util.List;

public interface Storage {
    void put(Banknote b);

    void put(List<Banknote> list);

    int getBalance();

    int getAmount(int value);

    List<Banknote> get(int value, int amount) throws AtmException;

    List<Banknote> getAll();
}
