package ru.otus.Tester;

public class Results {
    private int passed;
    private int failed;

    public void addFailed() {
        this.failed++;
    }

    public void addPassed() {
        this.passed++;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return failed;
    }
}
