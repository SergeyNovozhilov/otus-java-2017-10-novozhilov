package ru.otus.servlet;

import ru.otus.app.FrontendServiceMS;
import ru.otus.database.DbService.MsgGetData;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Message;
import ru.otus.messageSystem.MessageSystem;

public class FrontendServiceMsImpl implements FrontendServiceMS {

    private MessageSystem ms;

    private AdminWebSocketEndPoint admin;

    private final String name;

    private final Address address;

    public FrontendServiceMsImpl(MessageSystem ms, AdminWebSocketEndPoint admin) {
        this.ms = ms;
        this.admin = admin;
        this.address = new Address();
        this.name = "FrontEnd-" + address;
    }

    @Override
    public void handleRequest() {
        Address db = ms.lookUp("DbService");
        if (db != null) {
            Message message = new MsgGetData(getAddress(), db);
            ms.sendMessage(message);
        } else {
            System.out.println("AdminServlet: db is null");
        }
    }

    @Override
    public void setData(String data) {
        admin.sendMessage(data, this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MessageSystem getMS() {
        return ms;
    }
}
