package ru.otus.StorageImpl;

import ru.otus.Banknote.Banknote;

import java.util.HashMap;
import java.util.Map;

public class Memento {
    private final Map<Banknote, Integer> state;

    public Memento(Map<Banknote, Integer> state) {
        this.state = new HashMap<>(state);
    }

    public Map<Banknote, Integer> getSavedState() {
        return state;
    }
}
