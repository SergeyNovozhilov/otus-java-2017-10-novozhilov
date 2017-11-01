package ru.otus;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("----------- Test of addAll -------------------- ");

        List<String> lst = new MyArrayList<>();

        Collections.addAll(lst, "Fisrt", "Second", "Third");

        System.out.println("Value of list: ");
        lst.forEach(l -> System.out.println(l));

        System.out.println("----------- Test of copy -------------------- ");

        List<String> srclst = new MyArrayList<>(5);
        List<String> destlst = new MyArrayList<>(10);

        srclst.add("Java");
        srclst.add("is");
        srclst.add("best");

        destlst.add("C++");
        destlst.add("is");
        destlst.add("older");


        Collections.copy(destlst, srclst);

        System.out.println("Value of source list: ");
        srclst.forEach(s -> System.out.println(s));
        System.out.println("Value of destination list: ");
        destlst.forEach(d -> System.out.println(d));

        System.out.println("----------- Test of sort -------------------- ");

        List<String> fruits = new MyArrayList<>();

        fruits.add("Banana");
        fruits.add("Lemon");
        fruits.add("Apple");
        fruits.add("Pineapple");

        Collections.sort(fruits);
        System.out.println("Value of list in natural order: ");
        fruits.forEach(f -> System.out.println(f));

        Collections.sort(fruits, Collections.reverseOrder());
        System.out.println("Value of list in reverse order: ");
        fruits.forEach(f -> System.out.println(f));


    }
}
