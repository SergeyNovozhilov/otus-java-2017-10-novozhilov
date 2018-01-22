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
        cache = new CacheImpl(3, 1000, 800, false);
    }

    @Test
    public void testPutGet() {
        System.out.println("CacheImplTest: testPutGet");
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
    public void testMax() {
        System.out.println("CacheImplTest: testMax");

        Element<Integer, String> el1 = new Element<>(1, "one");
        Element<Integer, String> el2 = new Element<>(2, "one");
        Element<Integer, String> el3 = new Element<>(3, "one");
        Element<Integer, String> el4 = new Element<>(4, "one");

        cache.put(el1);
        cache.put(el2);
        cache.put(el3);

        sleep(100);

        Element<Integer, String> elRead = cache.get(1);

        if (elRead == null) {
            fail();
        }

        sleep(100);

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

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLifeTime() {
        System.out.println("CacheImplTest: testLifeTime");
        Element<Integer, String> el = new Element<>(1, "one");

        cache.put(el);

        sleep(400);

        Element<Integer, String> elRead = cache.get(1);

        if (elRead == null) {
            fail();
        }

        sleep(700);


        elRead = cache.get(1);
        if (elRead != null) {
            fail();
        }
    }

    @Test
    public void testIdleTime() {
        System.out.println("CacheImplTest: testIdleTime");
        Element<Integer, String> el1 = new Element<>(1, "one");
        Element<Integer, String> el2 = new Element<>(2, "one");

        cache.put(el1);
        cache.put(el2);


        Element<Integer, String> elRead1 = cache.get(1);

        if (elRead1 == null) {
            fail();
        }

        sleep(200);

        Element<Integer, String> elRead2 = cache.get(2);

        if (elRead2 == null) {
            fail();
        }

        sleep(500);


        elRead2 = cache.get(2);
        if (elRead2 == null) {
            fail();
        }

        elRead1 = cache.get(1);

        if (elRead1 != null) {
            fail();
        }

    }

    @After
    public void dispose() {
        cache.dispose();
    }
}
