package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClass {

    ObjectClass oc = new ObjectClass();

    @Before
    public void prepare() {
        System.out.println("Before");
        oc.setField("Object");
    }

    @Test
    public boolean runTest() {
        System.out.println("Test");
        if (oc.getField().equals("Object")) {
            return true;
        } else {
            return false;
        }
    }

    @After
    public void clear() {
        System.out.println("After");
        oc.setField("");
    }

}
