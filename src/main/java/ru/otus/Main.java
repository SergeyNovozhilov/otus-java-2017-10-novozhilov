package ru.otus;



public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        System.out.println("Size of empty String list: " + MemoryCalculator.calculate(list));
        for (int i = 0; i < 10; i++) {
            list.add("Hello: " + i);
        }
        System.out.println("Size of String list with 10 elements: " + MemoryCalculator.calculate(list));

    }
}
