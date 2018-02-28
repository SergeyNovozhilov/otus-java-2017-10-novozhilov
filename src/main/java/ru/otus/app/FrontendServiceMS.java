package ru.otus.app;

import ru.otus.messageSystem.Addressee;

/**
 * Created by tully.
 */
public interface FrontendServiceMS extends Addressee {

    void handleRequest();

    void setData(String data);
}

