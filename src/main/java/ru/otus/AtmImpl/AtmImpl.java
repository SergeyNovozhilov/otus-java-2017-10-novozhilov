package ru.otus.AtmImpl;

import ru.otus.Atm.Atm;
import ru.otus.Banknote.Banknote;
import ru.otus.Event.Event;
import ru.otus.Observer.Observer;
import ru.otus.Storage.Storage;
import ru.otus.StorageImpl.StorageMemory;

import java.util.ArrayList;
import java.util.List;

public class AtmImpl implements Atm, Observer {

    private final Storage storage;

    public AtmImpl() {
        storage = new StorageMemory();
    }

    public boolean startAtm(List<Banknote> banknotes, int amount) {
        return storage.initStorageMemory(banknotes, amount);
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

        List<Banknote> range = storage.range();

        if (sum % range.get(range.size() - 1).value() > 0) {
            System.err.format("Cannot collect %d", sum);
            return new ArrayList<>();
        }

        List<Banknote> list = new ArrayList<>();

        storage.save();

        for (int i = 0; i < range.size(); i++) {
            int amount = sum / range.get(i).value();
            if (amount == 0) {
                continue;
            }
            if (amount > storage.getAmount(range.get(i))) {
                amount = storage.getAmount(range.get(i));
            }
            list.addAll(storage.get(range.get(i), amount));
            sum -= amount * range.get(i).value();
            if (sum == 0) {
                break;
            }
        }

        if (sum > 0) {
            System.err.format("Cannot collect %d", sum);
            storage.restore();
            return new ArrayList<>();
        }

        return list;
    }

    @Override
    public int balance() {
        int sum = 0;
        for( Banknote b : storage.range()) {
            sum += storage.getAmount(b) * b.value();
        }
        return sum;
    }

    @Override
    public void notify(Event event) {
        String type = event.getType();
       switch (event.getType()) {
           case "RestoreInitialEvent":
               storage.restoreInitial();
               break;
           default:
               break;
       }
    }
}
