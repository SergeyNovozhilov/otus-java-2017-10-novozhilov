package ru.otus;

import ru.otus.annotations.Before;
import ru.otus.annotations.MyParam;
import ru.otus.annotations.MyParams;
import ru.otus.annotations.Test;

public class ObjectClassTest {

//    private static Object[] params = {"sTring", 12};

    ObjectClass oc = null;

    @Before
    public void prepare(@MyParam(index = 0)String str, @MyParam(index = 1)int i) {
        System.out.println("prepare ");
        oc = new ObjectClass();
        oc.setSField(str);
        oc.setIField(i);
    }


    @Test
    public void test() throws Exception {
        System.out.println("Test");
        if (!oc.getSField().equals(getParams()[0]) || oc.getIField() != (int)getParams()[1]) {
            throw new Exception(" Test Failed");
        }
    }

    @MyParams
    public static Object[] getParams() {
        Object[] params = {"sTring", 12};
        return params;
    }

}
