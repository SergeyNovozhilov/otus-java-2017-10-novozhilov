package ru.otus;


import com.google.gson.Gson;
import ru.otus.person.Address;
import ru.otus.person.Person;
import ru.otus.person.PhoneNumber;

import ru.otus.person.SomeClass;
import ru.otus.utils.ReflectionUtils;

import javax.json.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.setAddress(new Address("Street", 12345, "State", "City"));
        person.setAge(25);
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setPhoneNumber(new PhoneNumber[]{new PhoneNumber("123 12-131-12", "phone"),
                new PhoneNumber("123 12-131-13", "fax")});

        person.setStrings(Arrays.asList("one", "two"));

        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList("three", "four"));

        person.setSet(set);
        String[] arr = {"AAA", "BBB"};
        person.setArray(arr);

        System.out.println("----------------------");

        SomeClass sc = new SomeClass();

        sc.setArray(arr);

        JsonObjectBuilder job = Json.createObjectBuilder();

        ReflectionUtils.parseObject(person, job);

        JsonObject jo = job.build();

        System.out.println(jo.toString());
        System.out.println("=====================");

        Gson g = new Gson();

        String sg = g.toJson(person);

        System.out.println(sg);


    }

    public static void test () {
        JsonObjectBuilder job = Json.createObjectBuilder();
        JsonObject jo = job.addNull("srt").build();

        String s = jo.toString();

                System.out.println(" -- " + s);

//        Json.createArrayBuilder().add("");
//        JsonObject jo = new JsonObject();
//
//        JsonObject model = Json.createObjectBuilder()
//                .add("firstName", "Duke")
//                .add("age", 28)
//                .add("streetAddress", "100 Internet Dr")
//                .add("phoneNumbers", Json.createArrayBuilder().add()
//                        .add(Json.createObjectBuilder()
//                                .add("type", "home")
//                                .add("number", "222-222-2222")))
//                .build();
    }
}
