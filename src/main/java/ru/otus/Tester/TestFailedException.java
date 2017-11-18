package ru.otus.Tester;

public class TestFailedException extends Exception{
    public TestFailedException() {
    }

    public TestFailedException(String message) {
        super(message);
    }
}
