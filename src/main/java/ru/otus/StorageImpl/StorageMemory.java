package ru.otus.StorageImpl;

import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;
import ru.otus.BanknoteImpl.Roubles;
import ru.otus.Storage.Storage;

import java.util.*;
import java.util.stream.Collectors;

public class StorageMemory implements Storage {

    private Map<Banknote, Integer> moneys;

    private List<Memento> savedStates;

    private final int INITIAL = 0;
    private final int SAVED = 1;

    public StorageMemory(Banknote banknote, int initial) {
        init();
        savedStates = new ArrayList<>();
        for (Banknote b : banknote.getValues()) {
            moneys.put(b, initial);
        }
        savedStates.add(new Memento(moneys));
    }


    @Override
    public void put(Banknote b) {
        Integer num = moneys.getOrDefault(b, 0);
        moneys.put(b, ++num);
    }

    @Override
    public void put(List list) {
        list.forEach(b -> put((Banknote) b));
    }


    @Override
    public int getAmount(Banknote b) {
        return moneys.get(b);
    }


    @Override
    public List<Banknote> get(Banknote b, int amount) throws AtmException {
        if (moneys.isEmpty() || moneys.get(b) < amount) {
            throw new AtmException(String.format("Not enough banknotes of %d", b.value()));
        }
        moneys.put(b, moneys.get(b) - amount);
        return toBanknotes(b, amount);
    }

    @Override
    public List<Banknote> getAll() {
        List<Banknote> list = new ArrayList<>();
        for (Map.Entry e : moneys.entrySet()) {
            list.addAll(toBanknotes((Banknote)e.getKey(), (int)e.getValue()));
        }
        init();
        return list;
    }

    @Override
    public List<Banknote> range() {
        List<Banknote> list = new ArrayList<>(moneys.keySet());
        list.sort(Comparator.comparingInt(Banknote::value).reversed());
        return list;
    }

    @Override
    public void save() {
        if (savedStates.size() > 1) {
            savedStates.set(SAVED, new Memento(moneys));
        } else {
            savedStates.add(new Memento(moneys));
        }
    }

    @Override
    public void restore() {
        if (savedStates.size() > 1) {
            moneys = savedStates.remove(savedStates.size() - 1).getSavedState();
        } else {
            restoreInitial();
        }
    }

    @Override
    public void restoreInitial() {
        if (savedStates.size() > 0) {
            moneys = savedStates.get(INITIAL).getSavedState();
        } else {
            init();
        }
    }

    private List<Banknote> toBanknotes(Banknote b, int amount) {
        List<Banknote> list = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            list.add(b.copy());
        }

        return list;
    }

    private void init() {
        moneys = new HashMap<>();
    }

}
