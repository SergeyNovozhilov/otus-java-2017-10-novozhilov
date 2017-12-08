package ru.otus.EventImpl;

import ru.otus.Event.Event;

public class RestoreInitialEvent implements Event {
    @Override
    public String getType() {
        return "RestoreInitialEvent";
    }
}
