package ru.otus.messageSystem;

import java.util.UUID;

public final class Address {
    private final UUID id;

    public Address(){
        id = UUID.randomUUID();
    }

    public Address(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return address.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public UUID getId() {
        return id;
    }
}
