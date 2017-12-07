package ru.otus.Department;

import ru.otus.Atm.Atm;
import ru.otus.AtmImpl.AtmImpl;
import ru.otus.Banknote.Banknote;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<Atm> atms;

    public Department() {
        atms = new ArrayList<>();
    }

    public void addAtm(Banknote b, int initial) {
        atms.add(new AtmImpl(b, initial));
    }
}
