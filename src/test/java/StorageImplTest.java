import org.junit.Before;
import org.junit.Test;
import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;
import ru.otus.BanknoteImpl.Roubles;
import ru.otus.Storage.Storage;
import ru.otus.StorageImpl.StorageMemory;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class StorageImplTest {

    private Storage storage;

    @Before
    public void setUp() {
        System.out.println("setUp");
        storage = new StorageMemory(Roubles.fromValue(10), 0);
    }

    @Test
    public void testPut() {
        System.out.println("StorageImplTest: testPut");

        storage.put(Roubles.fromValue(10));

        assertTrue(storage.getAmount(Roubles.fromValue(10)) == 1);

        List<Banknote> list = Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100));
        storage.put(list);

        assertTrue(storage.getAmount(Roubles.fromValue(50)) == 1);
        assertTrue(storage.getAmount(Roubles.fromValue(100)) == 1);

    }


    @Test
    public void testGet() {
        System.out.println("StorageImplTest: testGet");

        List<Banknote> listOrig = Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100));
        storage.put(listOrig);

        List<Banknote> listRet = storage.getAll();
        assertNotNull(listRet);
        listRet.sort(Comparator.comparingInt(Banknote::value));
        listOrig.sort(Comparator.comparingInt(Banknote::value));
        assertEquals(listOrig, listRet);

        assertTrue(storage.getAll().isEmpty());



        try {
            storage.get(Roubles.fromValue(50), 2);
            fail();
        } catch (AtmException e) {
            assertThat(e.getMessage(), is("Not enough banknotes of 50"));
        }

        Banknote fifty = Roubles.fromValue(50);

        storage.put(fifty);

        try {
            List<Banknote> list = storage.get(Roubles.fromValue(50), 1);
            assertNotNull(listRet);
            assertTrue(list.size() == 1);
            assertEquals(list.get(0), fifty);

        } catch (AtmException e) {
            fail();
        }
    }

    @Test
    public void simpleTest() {
        Map<Integer, String> a = new HashMap<>();
        a.put(0, "null");
        a.put(1, "one");

        Map<Integer, String> b = new HashMap<>(a);

        a.put(0, "aaa");

        System.out.println("-- " + b.get(0));



    }
}
