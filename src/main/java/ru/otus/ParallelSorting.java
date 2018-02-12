package ru.otus;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class ParallelSorting {

    private int threadsNumber = 4;

    public void execute(Object[] array) throws InterruptedException {
        if (array == null || array.length == 0) {
            return;
        }
        CountDownLatch latch = new CountDownLatch(threadsNumber);

        int shift = array.length % threadsNumber == 0 ? array.length / threadsNumber : array.length / threadsNumber + 1;

        int start = 0;

        int end;

        for (int i = 0; i < threadsNumber; i++) {
            end = start + shift > array.length ? array.length : start + shift;
            int s = start;
            int e = end;
            new Thread(() -> {
                Arrays.sort(array, s, e);
                latch.countDown();
            }).start();
            start = end;
        }
        latch.await();

        Arrays.sort(array, 0, array.length);

    }

    public void setThreadsNumber(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }
}
