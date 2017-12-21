package ru.otus.SimpleJson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class JsonHelper {
    public JsonObjectBuilder parseObject(Object object) {

        if (object == null) {
            return null;
        }

        JsonObjectBuilder job = Json.createObjectBuilder();

        for (Field field : object.getClass().getDeclaredFields()) {
            boolean access = field.isAccessible();
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null) {
                    job.addNull(field.getName());
                    continue;
                }

                Class collectionIf = Arrays.asList(field.getType().getInterfaces()).stream().filter(c -> c.equals(Collection.class))
                        .findAny().orElse(null);
                if (field.getType().equals(String.class) || field.getType().isPrimitive()) {
                    job.add(field.getName(), value.toString());
                } else if (field.getType().isArray()) {
                    Object[] objects = (Object[]) value;
                    job.add(field.getName(), parseArray(objects));
                } else if ((collectionIf != null)) {
                    Collection collection = (Collection) value;
                    job.add(field.getName(), parseArray(collection.toArray()));
                } else {
                    job.add(field.getName(), parseObject(value));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            } finally {
                field.setAccessible(access);
            }
        }
        return job;
    }

    private JsonArrayBuilder parseArray(Object[] array) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                jab.addNull();
                continue;
            }
            if (array[i].getClass().equals(String.class) || array[i].getClass().isPrimitive()) {
                jab.add(array[i].toString());
            } else {
                jab.add(parseObject(array[i]));
            }
        }
        return jab;
    }
}

