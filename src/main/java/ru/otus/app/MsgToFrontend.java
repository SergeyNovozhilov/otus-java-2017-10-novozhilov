package ru.otus.app;

import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendServiceMS) {
            exec((FrontendServiceMS) addressee);
        }
    }

    public abstract void exec(FrontendServiceMS frontendService);
}