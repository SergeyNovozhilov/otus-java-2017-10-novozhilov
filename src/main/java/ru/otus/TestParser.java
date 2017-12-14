package ru.otus;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class TestParser {

    public void func(Object a) {
        for (Field f : a.getClass().getDeclaredFields()) {
            if (f.getName().equals("a")) {
                try {
                    func(f.get(a));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("name : " + f.getName());

            System.out.println("type : " + f.getType());

            Class clazz = Arrays.asList(f.getType().getInterfaces()).stream().filter(c -> c.equals(Collection.class))
                    .findAny().orElse(null);
            if (clazz != null) {
                System.out.println(" -- " + clazz.getCanonicalName());
            }

            f.setAccessible(true); // You might want to set modifier to public first.
            Object value = null;
            try {
                value = f.get(a);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
            if (value != null) {
                System.out.println(value);
            }
        }
    }
}
