package ru.otus.StorageImpl;

import ru.otus.Banknote.Banknote;
import ru.otus.Storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageMemory implements Storage{

    private final Map<Integer, List<Banknote>> moneys;

    public StorageMemory() {
        moneys = new HashMap<>();
    }

    @Override
    public void put(Banknote b) {
        List<Banknote> list = moneys.get(b.value());
        if (list == null) {
            list = new ArrayList<>();
            moneys.put(b.value(), list);
        }
        list.add(b);
    }

    @Override
    public void put(List<Banknote> list) {
        list.forEach(b -> put(b));
    }

    @Override
    public Map<Integer, List<Banknote>> get() {
        return moneys;
    }
}
