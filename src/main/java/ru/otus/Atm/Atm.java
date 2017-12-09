package ru.otus.Atm;

import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;

import java.util.List;

public interface  Atm {
    boolean startAtm(List<Banknote> banknotes, int amount);
    void put(List<Banknote> list);
    void put(Banknote b);
    List<Banknote> withdraw(int sum) throws AtmException;
    int balance();
}
