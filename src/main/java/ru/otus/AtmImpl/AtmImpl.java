package ru.otus.AtmImpl;

import ru.otus.Atm.Atm;
import ru.otus.Banknote.Banknote;
import ru.otus.Storage.Storage;
import ru.otus.StorageImpl.StorageMemory;

import java.util.ArrayList;
import java.util.List;

public class AtmImpl implements Atm{

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
    public List<Banknote> get(int sum) throws AtmException{
        if (sum > balance()) {
            throw new AtmException(String.format("Sum %s exceeds the ATM balance", sum));
        }
        if (sum == balance()) {
            return new ArrayList(storage.get().values());
        }

        return null;
    }

    @Override
    public int balance() {
        return 0;
    }
}
