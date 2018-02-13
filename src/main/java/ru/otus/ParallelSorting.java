package ru.otus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

public class ParallelSorting {

    private int threadsNumber = 4;

    private boolean reversed = false;

    public void execute(Object[] array) throws InterruptedException {
        if (array == null || array.length == 0) {
            return;
        }
        CountDownLatch latch = new CountDownLatch(threadsNumber);

        int shift = array.length % threadsNumber == 0 ? array.length / threadsNumber : array.length / threadsNumber + 1;

        int start = 0;

        int end;

        Comparator c = null;

        if (reversed) {
            c = Collections.reverseOrder();
        }

        Comparator comparator = c;

        for (int i = 0; i < threadsNumber; i++) {
            end = start + shift > array.length ? array.length : start + shift;
            int s = start;
            int e = end;
            new Thread(() -> {
                Arrays.sort(array, s, e, comparator);
                latch.countDown();
            }).start();
            start = end;
        }
        latch.await();

        Arrays.sort(array, comparator);

    }

    public void setThreadsNumber(int threadsNumber) {
        this.threadsNumber = threadsNumber;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }
}
