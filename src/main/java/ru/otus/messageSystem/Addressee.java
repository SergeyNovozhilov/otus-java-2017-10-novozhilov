package ru.otus.messageSystem;


public interface Addressee {
    Address getAddress();

    String getName();

    boolean equals(Addressee addressee);
}
