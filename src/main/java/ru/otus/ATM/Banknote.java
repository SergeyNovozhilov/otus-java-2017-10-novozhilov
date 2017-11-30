package ru.otus.ATM;

public enum Banknote {
    TEN(10), FIFTY(50), ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000);

    private int value;

    Banknote(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static Banknote fromValue(int value) {
        for (Banknote b : Banknote.values()) {
            if (b.value == value) {
                return b;
            }
        }
        return null;
    }
}
