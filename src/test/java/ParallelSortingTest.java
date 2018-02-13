import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.ParallelSorting;

import java.util.Random;

import static org.junit.Assert.fail;


public class ParallelSortingTest {
    private Integer[] array;
    private int ARRAY_LENGTH = 12345600;
    private int THREADS_NUMBER = 4;

    @Before
    public void setUp() {
        array = new Integer[ARRAY_LENGTH];
        Random rn = new Random();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            array[i] = rn.nextInt(1000);
        }
    }

    @Test
    public void testNotReversed() {
        ParallelSorting ps = new ParallelSorting();
        ps.setThreadsNumber(THREADS_NUMBER);
        try {
            ps.execute(array);
            if (array == null || array.length == 0) {
                fail();
            }

            for (int i = 0; i < ARRAY_LENGTH; i++) {
                int j = i + 1 < ARRAY_LENGTH ? i + 1 : ARRAY_LENGTH - 1;
                Assert.assertTrue(array[i] <= array[j]);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testReversed() {
        ParallelSorting ps = new ParallelSorting();
        ps.setThreadsNumber(THREADS_NUMBER);
        ps.setReversed(true);

        try {
            ps.execute(array);
            if (array == null || array.length == 0) {
                fail();
            }

            for (int i = 0; i < ARRAY_LENGTH; i++) {
                int j = i + 1 < ARRAY_LENGTH ? i + 1 : ARRAY_LENGTH - 1;
                Assert.assertTrue(array[i] >= array[j]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
    }
}
