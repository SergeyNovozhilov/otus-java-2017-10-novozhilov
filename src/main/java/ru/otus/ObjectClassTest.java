package ru.otus;

import ru.otus.annotations.Before;
import ru.otus.annotations.MyParam;
import ru.otus.annotations.MyParams;
import ru.otus.annotations.Test;

public class ObjectClassTest {

    private static Object[] params = {"sTring"};

    ObjectClass oc = null;

//    @Before
//    public void prepare2() {
//        System.out.println("Before 2 ");
//        oc = new ObjectClass();
//        oc.setField("Object");
//    }

    @Before
    public void prepare1(@MyParam(index = 0)String str) {
        System.out.println("Before 1 ");
        oc = new ObjectClass();
        oc.setField(str);
    }



    @Test
    public void runTest1() throws Exception {
        System.out.println("Test1");
        if (!oc.getField().equals("Object")) {
            throw new Exception(" Test 1 Failed");
        }
    }
//
//    @Test
//    public void runTest2() throws Exception {
//        System.out.println("Test2");
//        if (!oc.getField().equals("")) {
//            throw new Exception(" Test 2 Failed");
//        }
//    }
//
//    @Test
//    public boolean runTest3() throws Exception{
//        System.out.println("Test3");
//        throw new Exception("Test 3 always failed");
//    }
//
//    @After
//    public void clear() {
//        System.out.println("After");
//        oc = null;
//    }

    @MyParams
    public static Object[] getParams() {
        return params;
    }

}
