package ru.otus;

import ru.otus.Tester.TestFailedException;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class ObjectClassTest {

    ObjectClass oc = null;

    @Before
    public void prepare1() {
        System.out.println("Before 1 ");
        oc = new ObjectClass();
        oc.setField("");
    }

    @Before
    public void prepare2() {
        System.out.println("Before 2 ");
        oc = new ObjectClass();
        oc.setField("Object");
    }

    @Test
    public void runTest1() throws TestFailedException {
        System.out.println("Test1");
        if (!oc.getField().equals("Object")) {
            throw new TestFailedException(" Test 1 Failed");
        }
    }

    @Test
    public void runTest2() throws TestFailedException {
        System.out.println("Test2");
        if (!oc.getField().equals("")) {
            throw new TestFailedException(" Test 2 Failed");
        }
    }

    @Test
    public boolean runTest3() throws TestFailedException{
        System.out.println("Test3");
        throw new TestFailedException("Test3 always failed");
    }

    @After
    public void clear() {
        System.out.println("After");
        oc = null;
    }

}
