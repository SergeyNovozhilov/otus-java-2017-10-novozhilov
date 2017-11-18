package ru.otus.Tester;

import com.google.common.reflect.ClassPath;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tester {

    private Tester() {
    }

    public static void run(String name) {
        final List<Results> results = new ArrayList<>();
        try {
            Class clazz = Class.forName(name);
            results.add(execute(clazz));
        } catch (ClassNotFoundException e) {
            try {
                ClassPath path = ClassPath.from(Thread.currentThread().getContextClassLoader());
                List<ClassPath.ClassInfo> clazzInPackage = new ArrayList<>(path.getTopLevelClasses(name));
                if (clazzInPackage.size() > 0) {
                    clazzInPackage.forEach(c -> {
                        try {
                            Class clazz = Class.forName(c.getName());
                            results.add(execute(clazz));
                        } catch (ClassNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    });
                } else {
                    System.out.println("Error. " + name + " ether not a class or an empty package.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (results.size() > 0) {

            int passed = results.stream().collect(Collectors.summingInt(Results::getPassed));
            int failed = results.stream().collect(Collectors.summingInt(Results::getFailed));
            System.out.println("Passed: " + passed + " Failed: " + failed);
        } else {
            System.out.println("Test didn't run");
        }
    }

    private static Results execute(Class clazz) {
        Map<Method, Class[]> befores = new HashMap<>();
        Map<Method, Class[]> tests = new HashMap<>();
        Map<Method, Class[]> afters = new HashMap<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                befores.put(method, method.getParameterTypes());
                continue;
            }

            if (method.isAnnotationPresent(Test.class)) {
                tests.put(method, method.getParameterTypes());
                continue;
            }

            if (method.isAnnotationPresent(After.class)) {
                afters.put(method, method.getParameterTypes());
            }
        }
        Results results = new Results();
        tests.forEach((key, value) -> {
            Object test = ReflectionHelper.instantiate(clazz);
            if (test != null) {
                try {
                    for (Map.Entry e : befores.entrySet()) {
                        ReflectionHelper.callMethod(test, (Method) e.getKey(), (Class[]) e.getValue());
                    }
                    try {
                        ReflectionHelper.callMethod(test, key, value);
                        results.addPassed();
                    } catch (Exception ex) {
                        System.out.println(ex.getCause());
                        results.addFailed();
                    }
                    for (Map.Entry e : afters.entrySet()) {
                        ReflectionHelper.callMethod(test, (Method) e.getKey(), (Class[]) e.getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                System.out.println("Can not instantiate object " + clazz.getName() + ". Exit.");
            }
        });
        return results;
    }
}
