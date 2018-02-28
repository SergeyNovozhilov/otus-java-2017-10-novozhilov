package ru.otus.database.DbService;

import ru.otus.app.FrontendServiceMS;
import ru.otus.app.MsgToFrontend;
import ru.otus.messageSystem.Address;


public class MsgGetDataAnswer extends MsgToFrontend {
    private final String data;

    public MsgGetDataAnswer(Address from, Address to, String data) {
        super(from, to);
        this.data = data;
    }

    @Override
    public void exec(FrontendServiceMS frontendService) {
        frontendService.setData(data);
    }
}
