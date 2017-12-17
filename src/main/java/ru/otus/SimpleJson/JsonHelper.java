package ru.otus.SimpleJson;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public class JsonHelper {
    public void parseObject(Object object, JsonObjectBuilder job) {

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
                    job.add(field.getName(), parseArray(objects));
                } else if ((collectionIf != null)) {
                    Collection collection = (Collection) value;
                    job.add(field.getName(), parseArray(collection.toArray()));
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

    private JsonArrayBuilder parseArray(Object[] array) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i].getClass().equals(String.class) || array[i].getClass().isPrimitive()) {
                jab.add(array[i].toString());
            } else {
                JsonObjectBuilder j = Json.createObjectBuilder();
                parseObject(array[i], j);
                jab.add(j);
            }
        }
        return jab;
    }
}

