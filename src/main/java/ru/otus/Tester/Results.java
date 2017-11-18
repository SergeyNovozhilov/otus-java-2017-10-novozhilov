package ru.otus.Tester;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Results {
    private int passed;
    private int failed;

    public void addFailed() {
        this.failed++;
    }

    public void addPassed() {
        this.passed++;
    }
}
