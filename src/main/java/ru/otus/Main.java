package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;

        List<Method> befores = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> afters = new ArrayList<>();


        System.out.println("length: " + testClass.getDeclaredMethods().length);

        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                System.out.println("Before");
                befores.add(method);
                continue;
            }

            if (method.isAnnotationPresent(Test.class)) {
                System.out.println("Test");
                tests.add(method);
                continue;
            }

            if (method.isAnnotationPresent(After.class)) {
                System.out.println("After");
                afters.add(method);
            }
        }

        tests.forEach(t -> {
            TestClass test = null;
            try {
                test = testClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            TestClass finalTest = test;
            befores.forEach(b -> {
                try {
                    b.invoke(finalTest);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            try {
                t.invoke(test);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            TestClass finalTest1 = test;
            afters.forEach(a -> {
                try {
                    a.invoke(finalTest1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
