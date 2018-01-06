package ru.otus.Tester;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionHelper {
    private ReflectionHelper() {
    }

    static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                return type.getConstructor(toClasses(args)).newInstance(args);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Object callMethod(Object object, Method method, Object... args) throws Exception {
        boolean isAccessible = true;
        try {
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            if (args.length == 0) {
                return method.invoke(object);
            }

            return method.invoke(object, args);

        } catch (Exception e) {
            throw e;
        } finally {
            if (method != null) {
                method.setAccessible(isAccessible);
            }
        }
    }

    static Object callMethod(Object object, Method method) throws Exception {
        boolean isAccessible = true;
        try {
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object);
        } catch (Exception e) {
            throw e;
        } finally {
            if (method != null) {
                method.setAccessible(isAccessible);
            }
        }
    }

    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args).map(Object::getClass).collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }
}
