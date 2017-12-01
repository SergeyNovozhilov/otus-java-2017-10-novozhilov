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
    public List<Banknote> get(int sum) throws AtmException {
        if (sum > balance()) {
            throw new AtmException(String.format("Sum %s exceeds the ATM balance", sum));
        }
        if (sum == storage.getBalance()) {
            return storage.getAll();
        }

        List<Integer> range = Roubles.range();

        if (sum % range.get(range.size() - 1) > 0) {
            throw new AtmException(String.format("Cannot collect ", sum));
        }

        List<Banknote> list = new ArrayList<>();
        int returnSum = 0;

        for (int i = 0; i < range.size(); i++) {
            int amount = sum / range.get(i);
            if (amount == 0) {
                continue;
            }
            list.addAll(storage.get(range.get(i), amount));
            returnSum += amount * range.get(i);
            if (returnSum == sum) {
                break;
            }
        }

        if (sum > returnSum) {
            throw new AtmException(String.format("Cannot collect ", sum));
        }

        return list;
    }

    @Override
    public int balance() {
        return storage.getBalance();
    }
}
