package ru.otus.utils;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ReflectionUtils {
    public static void parseObject(Object object, JsonObjectBuilder job) {

        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                Class collectionIf = Arrays.asList(field.getType().getInterfaces()).stream().filter(c -> c.equals(Collection.class))
                        .findAny().orElse(null);
                if (field.getType().equals(String.class) || field.getType().isPrimitive()) {
                    job.add(field.getName(), value.toString());
                } else if (field.getType().isArray()) {
                    Object[] objects = (Object[]) value;
                    JsonArrayBuilder jab = Json.createArrayBuilder();

                    for (int i = 0; i < objects.length; i++) {
                        if (objects[i].getClass().equals(String.class) || objects[i].getClass().isPrimitive()) {
                            jab.add(objects[i].toString());
                        } else {
                            JsonObjectBuilder j = Json.createObjectBuilder();
                            parseObject(objects[i], j);
                            jab.add(j);
                        }
                    }
                    job.add(field.getName(), jab);
                } else if ((collectionIf != null)) {
                    Collection collection = (Collection) value;
                    JsonArrayBuilder jab = Json.createArrayBuilder();
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        Object obj = it.next();
                        if (obj.getClass().equals(String.class) || obj.getClass().isPrimitive()) {
                            jab.add(obj.toString());
                        } else {
                            JsonObjectBuilder j = Json.createObjectBuilder();
                            parseObject(obj, j);
                            jab.add(j);
                        }
                    }
                    job.add(field.getName(), jab);
                } else {
                    JsonObjectBuilder j = Json.createObjectBuilder();
                    parseObject(value, j);
                    job.add(field.getName(), j);
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

    }
}

