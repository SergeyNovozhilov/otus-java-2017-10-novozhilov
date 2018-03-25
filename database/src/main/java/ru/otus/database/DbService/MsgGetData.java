package ru.otus.database.DbService;

import ru.otus.app.DBServiceMS;
import ru.otus.app.MsgToDB;
import ru.otus.messageSystem.Address;

public class MsgGetData extends MsgToDB {

    public MsgGetData(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DBServiceMS dbService) {
        String data = dbService.getData();
        dbService.getMS().sendMessage(new MsgGetDataAnswer(getTo(), getFrom(), data));
    }
}
