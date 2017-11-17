package ru.otus;

import com.google.common.reflect.ClassPath;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Class<TestClass> testClass = TestClass.class;

        List<Method> befores = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> afters = new ArrayList<>();


//        Reflections reflections = new Reflections("ru.otus");
//
//        Set<Class<? extends  Object>> allClasses =
//                reflections.getSubTypesOf(Object.class);
//
//        if (allClasses != null) {
//            System.out.println("size: " + allClasses.size());
//            allClasses.forEach(c -> {
//                System.out.println("---");
//                System.out.println(c.getCanonicalName());
//            });
//        } else {
//            System.out.println("null");
//        }


        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
//        classLoadersList.add(ClasspathHelper.staticClassLoader());
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0]))));
        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);


        if (allClasses != null) {
            System.out.println("size: " + allClasses.size());
            allClasses.forEach(c -> {
                try {
                    if (c == null || c.getPackage() == null || c.getPackage().getName() == null) {
                        return;
                    }
                    if (c.getPackage().getName().equals("ru.otus")) {
                        System.out.println(c.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            System.out.println("null");
        }

        ClassPath path = null;
        try {
            path = ClassPath.from(Thread.currentThread().getContextClassLoader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ClassPath.ClassInfo> clazzInPackage =
                new ArrayList<ClassPath.ClassInfo>(path.getTopLevelClasses("ru.otus"));


        clazzInPackage.forEach(c -> {
            System.out.println("-> " + c.getName());
        });


        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                befores.add(method);
                continue;
            }

            if (method.isAnnotationPresent(Test.class)) {
                tests.add(method);
                continue;
            }

            if (method.isAnnotationPresent(After.class)) {
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
