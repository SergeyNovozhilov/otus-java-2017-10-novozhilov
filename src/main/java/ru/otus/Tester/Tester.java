package ru.otus.Tester;

import com.google.common.reflect.ClassPath;
import ru.otus.annotations.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
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
        List<Method> befores = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> afters = new ArrayList<>();

        Object[] params = null;

        for (Method method : clazz.getDeclaredMethods()) {
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

            if (method.isAnnotationPresent(MyParams.class)) {
                Object object = ReflectionHelper.instantiate(clazz);
                try {
                    params = (Object[])ReflectionHelper.callMethod(object, method);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Results results = new Results();
        Object[] finalParams = params;
        tests.forEach(test -> {
            Object testObject = ReflectionHelper.instantiate(clazz);
            if (testObject != null) {
                try {
                    for (Method before : befores) {
                        ReflectionHelper.callMethod(testObject, before, getParams(before, finalParams));
                    }
                    try {
                        ReflectionHelper.callMethod(testObject, test, getParams(test, finalParams));
                        results.addPassed();
                    } catch (Exception ex) {
                        System.out.println(ex.getCause());
                        results.addFailed();
                    }
                    for (Method after : afters) {
                        ReflectionHelper.callMethod(testObject, after, getParams(after, finalParams));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                System.out.println("Cannot instantiate object " + clazz.getName() + ". Exit.");
            }
        });
        return results;
    }

    private static Object[] getParams(Method method, Object[] finalParams)  {
        List<Object> objects = new ArrayList<>();
        Annotation[][] annotations = method.getParameterAnnotations();
        if (annotations.length == 0) {
            return new Object[0];
        }
        for (Annotation[] anns : annotations) {
            for (Annotation ann : anns) {
                if (ann instanceof MyParam) {
                    MyParam mp = (MyParam)ann;
                    objects.add(finalParams[mp.index()]);
                }
            }
        }
        return objects.toArray();
    }
}
