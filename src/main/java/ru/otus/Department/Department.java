package ru.otus.Department;

import ru.otus.Atm.Atm;
import ru.otus.AtmImpl.AtmImpl;
import ru.otus.Banknote.Banknote;
import ru.otus.Event.Event;
import ru.otus.EventImpl.RestoreInitialEvent;
import ru.otus.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<Atm> atms;
    private final List<Observer> subscribers;


    public Department() {
        atms = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    public Atm addAtm(Banknote b) {
        return new AtmImpl(b, 0);
    }

    public Atm addAtm(Banknote b, int initial) {
        Atm atm = new AtmImpl(b, initial);
        atms.add(atm);
        return atm;
    }

    public boolean register(Atm atm) {
        if (atm instanceof Observer) {
            return subscribers.add((Observer) atm);
        }
        System.err.println("Not a Observer.");
        return false;
    }

    public boolean unregister(Atm atm) {
        return subscribers.remove(atm);
    }

    public void restoreInitial() {
        Event event = new RestoreInitialEvent();

        subscribers.forEach(s -> s.notify(event));
    }
}
