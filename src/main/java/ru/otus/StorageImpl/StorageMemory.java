package ru.otus.StorageImpl;

import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;
import ru.otus.Storage.Storage;

import java.util.*;

public class StorageMemory implements Storage {

    private Map<Integer, List<Banknote>> moneys;

    public StorageMemory() {
        init();
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
    public void put(List list) {
        list.forEach(b -> put((Banknote) b));
    }


    @Override
    public int getAmount(int value) {
        return moneys.get(value).size();
    }


    @Override
    public List<Banknote> get(int value, int amount) throws AtmException {
        if (moneys.get(value).size() < amount) {
            throw new AtmException(String.format("Not enough banknotes of %d", value));
        }
        List<Banknote> values = moneys.get(value);
        List<Banknote> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            list.add(values.remove(values.size() - 1));
        }
        return list;
    }

    @Override
    public List<Banknote> getAll() {
        List<Banknote> list = new ArrayList<>();
        moneys.values().stream().forEach(l -> list.addAll(l));
        init();
        return list;
    }

    @Override
    public List<Integer> range() {
        List<Integer> list = new ArrayList<>(moneys.keySet());
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    private void init() {
        moneys = new HashMap<>();
    }

}
