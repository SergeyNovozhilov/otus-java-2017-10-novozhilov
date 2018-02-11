package ru.otus;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static int  MAX_THREADS = 4;
    private static int ARRAY_LENGTH = 21;

    public static void main(String[] args) throws InterruptedException{
        Integer[] array = new Integer[ARRAY_LENGTH];
        Random rn = new Random();
        CountDownLatch latch = new CountDownLatch(MAX_THREADS);
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            array[i] = rn.nextInt(1000);
        }

        for (int i: array) {
            System.out.println(i);
        }
        int shift = ARRAY_LENGTH % MAX_THREADS == 0 ? ARRAY_LENGTH / MAX_THREADS : ARRAY_LENGTH / MAX_THREADS + 1;

        int start = 0;

        int end;

        for (int i = 0; i < MAX_THREADS; i++) {
            end = start + shift > ARRAY_LENGTH - 1 ? ARRAY_LENGTH - 1 : start + shift;
            new Thread(new Sort(array, start, end, latch)).start();
            start = end - 1;
        }


        latch.await();

        Arrays.sort(array, 0, ARRAY_LENGTH);

        System.out.println("=================================");

        for (int i: array) {
            System.out.println(i);
        }
    }

}
