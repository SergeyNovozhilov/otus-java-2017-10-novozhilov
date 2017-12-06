package ru.otus.Banknote;

public interface Banknote {
    int value();
    Banknote copy();
    Banknote[] getValues();
}
