package ru.otus;


import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> s = new MyArrayList<>();
        List<String> fruits = new MyArrayList<>();

        fruits.add("pineapple");
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("banana");

        Collections.addAll(fruits, "cherry", "lemon", "fruit");

        int i=0;
        for(String temp: fruits){
            System.out.println("fruits " + ++i + " : " + temp);
        }
    }
}
