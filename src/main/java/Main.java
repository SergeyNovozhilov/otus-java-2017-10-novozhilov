import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static int MAX_NUMBER = 100;

    public static void main(String ... args) {
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < MAX_NUMBER; i++) {
            intList.add((int)(Math.random() * MAX_NUMBER + 1));
        }
        Ordering ordering = Ordering.natural();
        System.out.println("Unsorted:");
        System.out.println(intList);
        Collections.sort(intList, ordering);
        System.out.println("Sorted:");
        System.out.println(intList);
    }

}
