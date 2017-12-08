package ru.otus.Observer;

import ru.otus.Event.Event;

public interface Observer {
    void notify(Event event);
}
