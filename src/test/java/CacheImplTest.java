import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.cache.Cache;
import ru.otus.cache.CacheImpl;
import ru.otus.cache.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CacheImplTest {

    private Cache cache;

    @Before
    public void setUp() {
        cache = new CacheImpl(3, 1000, 10000, false);
    }

    @Test
    public void test1() {
        System.out.println("CacheImplTest: test1");
        Element<Integer, String> el = new Element<>(1, "one");

        cache.put(el);

        Element<Integer, String> elRead = cache.get(1);

        if (elRead == null) {
            fail();
        }

        assertEquals(el.getKey(), elRead.getKey());
        assertEquals(el.getValue(), elRead.getValue());
    }

    @Test
    public void test2() {
        System.out.println("CacheImplTest: test2");

        Element<Integer, String> el1 = new Element<>(1, "one");
        Element<Integer, String> el2 = new Element<>(2, "one");
        Element<Integer, String> el3 = new Element<>(3, "one");
        Element<Integer, String> el4 = new Element<>(4, "one");

        cache.put(el1);
        cache.put(el2);
        cache.put(el3);

        sleep();

        Element<Integer, String> elRead = cache.get(1);

        if (elRead == null) {
            fail();
        }

        sleep();

        elRead = cache.get(3);

        if (elRead == null) {
            fail();
        }

        cache.put(el4);

        elRead = cache.get(4);

        if (elRead == null) {
            fail();
        }

        elRead = cache.get(2);

        if (elRead != null) {
            fail();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        System.out.println("CacheImplTest: test3");
        Element<Integer, String> el = new Element<>(1, "one");

        cache.put(el);

        Element<Integer, String> elRead = cache.get(1);

        if (elRead == null) {
            fail();
        }

        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }

        elRead = cache.get(1);
        if (elRead != null) {
            fail();
        }
    }

    @After
    public void dispose() {
        cache.dispose();
    }
}
