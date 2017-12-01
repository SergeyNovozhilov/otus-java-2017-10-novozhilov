package ru.otus.StorageImpl;

import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;
import ru.otus.Storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageMemory implements Storage {

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
    public int getBalance() {
        if (moneys.isEmpty()) {
            return 0;
        }
        int sum = 0;
        moneys.entrySet().stream().map((entry) -> entry.getKey() * entry.getValue().size()).reduce(sum, Integer::sum);
        return sum;
    }


    @Override
    public int getAmount(int value) {
        return moneys.get(value).size();
    }


    @Override
    public List<Banknote> get(int value, int amount) throws AtmException {
        if (moneys.get(value).size() < amount) {
            throw new AtmException(String.format("Not enough banknotes of ", value));
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
        return new ArrayList(moneys.values());
    }

}
