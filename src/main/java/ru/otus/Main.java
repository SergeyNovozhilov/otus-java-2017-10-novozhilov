package ru.otus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        System.out.println("Size of empty String list: " + MemoryCalculator.calculate(list));
        for (int i = 0; i < 10; i++) {
            list.add("Hello " + i);
        }
        System.out.println("Size of String list with 10 elements: " + MemoryCalculator.calculate(list));

        System.out.println("Size of empty string: " + MemoryCalculator.emptyString());

        System.out.println("Size of empty string as an object: " + MemoryCalculator.calculate(""));

        System.out.println("Size of 'Hello' string: " + MemoryCalculator.calculate("Hello"));

        System.out.println("Size of TestClass: " + MemoryCalculator.calculate(new TestClass()));

        System.out.println("Size of empty String array: " + MemoryCalculator.calculate(new String[0]));

        System.out.println("Size of String array with 10 elements: " + MemoryCalculator.calculate(new String[10]));

        Set<String> set = new HashSet<>();
        System.out.println("Size of empty String set: " + MemoryCalculator.calculate(set));
        for (int i = 0; i < 10; i++) {
            set.add("Hello " + i);
        }
        System.out.println("Size of String set with 10 elements: " + MemoryCalculator.calculate(list));
    }
}
