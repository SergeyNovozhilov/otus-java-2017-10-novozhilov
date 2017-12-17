package ru.otus.SimpleJson;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class SimpleJson {

    private JsonHelper jHelper;

    public SimpleJson() {
        jHelper = new JsonHelper();
    }

    public String toJson(Object object) {
        if (object == null) {
            return null;
        }
        JsonObjectBuilder job = Json.createObjectBuilder();

        jHelper.parseObject(object, job);

        JsonObject jo = job.build();

        return jo.toString();

    }
}
