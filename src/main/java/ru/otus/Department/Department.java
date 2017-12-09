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

    public boolean addAtm(Atm atm) {
        return atms.add(atm);
    }

    public boolean removeAtm(Atm atm) {
        return atms.remove(atm);
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

    public int getTotalBalance() {
        int sum = atms.stream().mapToInt(Atm::balance).sum();
        return sum;
    }
}
