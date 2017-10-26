package ru.otus;

import java.util.*;
import java.util.concurrent.Callable;

public class MemoryCalculator {
    private static int SIZE = 6000000;

    public static long emptyString(){
        return getSize(() -> {
            return new String(new char[0]);
        });
    }

    public static long calculate(Object object){
        if (object == null) {
            return 0l;
        }
        return getSize(() -> {
            return object.getClass().newInstance();
        });
    }

    public static long calculate(Object[] array) {
        if (array == null) {
            return 0l;
        }
        return getSize(() -> {
            Object[] item = new Object[array.length];
            System.arraycopy(array, 0, item, 0, array.length);
            return item;
        });
    }

    public static long calculate(Collection collection){
        if (collection == null) {
            return 0l;
        }
        return getSize(() -> {
            Collection item = collection.getClass().newInstance();
            item.addAll(collection);
            return item;
        });
    }


    public static long calculate(Map map){
        if (map == null) {
            return 0l;
        }
        return getSize(() -> {
            Map<Object, Object> item = map.getClass().newInstance();
            item.putAll(map);
            return item;
        });
    }

    private static long getSize(Callable func) {
        long memSize = 0l;
        try {
            Object[] array = new Object[SIZE];
            Runtime rt = Runtime.getRuntime();
            rt.gc();
            Thread.sleep(100);
            long memSizeBefore = rt.totalMemory() - rt.freeMemory();
            for (int i = 0; i < SIZE; i++) {
                array[i] = func.call();
            }
            long memSizeAfter = rt.totalMemory() - rt.freeMemory();
            rt.gc();
            memSize = (memSizeAfter - memSizeBefore) / SIZE;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }
        return memSize;
    }
}
