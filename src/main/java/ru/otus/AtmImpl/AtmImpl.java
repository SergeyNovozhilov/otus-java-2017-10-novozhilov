package ru.otus.AtmImpl;

import ru.otus.Atm.Atm;
import ru.otus.Banknote.Banknote;
import ru.otus.BanknoteImpl.Roubles;
import ru.otus.Storage.Storage;
import ru.otus.StorageImpl.StorageMemory;

import java.util.ArrayList;
import java.util.List;

public class AtmImpl implements Atm {

    private final Storage storage;

    public AtmImpl() {
        this.storage = new StorageMemory();
    }

    @Override
    public void put(List<Banknote> list) {
        storage.put(list);
    }

    @Override
    public void put(Banknote b) {
        storage.put(b);
    }

    @Override
    public List<Banknote> withdraw(int sum) throws AtmException {
        if (sum > balance()) {
            System.err.format("Sum %s exceeds the ATM balance", sum);
            return new ArrayList<>();
        }
        if (sum == balance()) {
            return storage.getAll();
        }

        List<Integer> range = storage.range();

        if (sum % range.get(range.size() - 1) > 0) {
            System.err.format("Cannot collect %d", sum);
            return new ArrayList<>();
        }

        List<Banknote> list = new ArrayList<>();

        for (int i = 0; i < range.size(); i++) {
            int amount = sum / range.get(i);
            if (amount == 0) {
                continue;
            }
            if (amount > storage.getAmount(range.get(i))) {
                amount = storage.getAmount(range.get(i));
            }
            list.addAll(storage.get(range.get(i), amount));
            sum -= amount * range.get(i);
            if (sum == 0) {
                break;
            }
        }

        if (sum > 0) {
            System.err.format("Cannot collect %d", sum);
            return new ArrayList<>();
        }

        return list;
    }

    @Override
    public int balance() {
        int sum = 0;
        for( int i : storage.range()) {
            sum += storage.getAmount(i) * i;
        }
        return sum;
    }
}
