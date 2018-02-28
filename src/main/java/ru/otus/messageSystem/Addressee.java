package ru.otus.messageSystem;


public interface Addressee {
    Address getAddress();

    String getName();

    MessageSystem getMS();
}
