package ru.otus;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Sort implements Runnable{

    private final Object[] array;
    private final int start;
    private final int end;
    private final CountDownLatch latch;

    public Sort(Object[] array, int start, int end, CountDownLatch latch) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.latch = latch;
    }

    @Override
    public void run() {
        Arrays.sort(array, start, end);
        latch.countDown();
    }
}
