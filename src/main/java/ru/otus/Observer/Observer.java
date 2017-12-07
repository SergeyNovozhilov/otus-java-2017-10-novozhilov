package ru.otus.Observer;

import ru.otus.Events.Event;

@FunctionalInterface
public interface Observer {
    void notify(Event event);
}
