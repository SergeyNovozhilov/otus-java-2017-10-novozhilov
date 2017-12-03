import org.junit.Before;
import org.junit.Test;
import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;
import ru.otus.BanknoteImpl.Roubles;
import ru.otus.Storage.Storage;
import ru.otus.StorageImpl.StorageMemory;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class StorageImplTest {

    private Storage storage;

    @Before
    public void setUp() {
        storage = new StorageMemory();
    }

    @Test
    public void testPut() {
        System.out.println("StorageImplTest: testPut");

        storage.put(Roubles.fromValue(10));

        assertTrue(storage.getAmount(10) == 1);

        List<Banknote> list = Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100));
        storage.put(list);

        assertTrue(storage.getAmount(50) == 1);
        assertTrue(storage.getAmount(100) == 1);

    }


    @Test
    public void testGet() {
        System.out.println("StorageImplTest: testGet");

        List<Banknote> listOrig = Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100));
        storage.put(listOrig);

        List<Banknote> listRet = storage.getAll();
        assertNotNull(listRet);
        assertEquals(listOrig, listRet);

        assertTrue(storage.getAll().isEmpty());

        Banknote fifty = Roubles.fromValue(50);

        storage.put(fifty);

        try {
            storage.get(50, 2);
            fail();
        } catch (AtmException e) {
            assertThat(e.getMessage(), is("Not enough banknotes of 50"));
        }

        try {
            List<Banknote> list = storage.get(50, 1);
            assertNotNull(listRet);
            assertTrue(list.size() == 1);
            assertEquals(list.get(0), fifty);

        } catch (AtmException e) {
            fail();
        }
    }
}
