import org.junit.Before;
import org.junit.Test;
import ru.otus.Atm.Atm;
import ru.otus.AtmImpl.AtmException;
import ru.otus.AtmImpl.AtmImpl;
import ru.otus.Banknote.Banknote;
import ru.otus.BanknoteImpl.Roubles;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class AtmImplTest {

    private Atm atm;

    @Before
    public void setUp() {
        atm = new AtmImpl();
    }


    @Test
    public void testPut() {
        System.out.println("AtmImplTest: testPut");

        atm.put(Roubles.fromValue(10));
        assertTrue(atm.balance() == 10);

        atm.put(Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100)));
        assertTrue(atm.balance() == 160);

    }

    @Test
    public void testWithdraw() {
        System.out.println("AtmImplTest: testWithdraw");

        atm.put(Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100), Roubles.fromValue(50), Roubles.fromValue(10)));

        try {
            List<Banknote> list = atm.withdraw(60);
            assertNotNull(list);
            assertTrue(list.size() == 2);
            assertTrue(atm.balance() == 150);

            list = atm.withdraw(160);
            assertNotNull(list);
            assertTrue(list.isEmpty());

        } catch (AtmException e) {
            fail();
        }
    }

    @Test
    public void testWithdrawAmount() {
        System.out.println("AtmImplTest: testWithdrawAmount");

        atm.put(Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100), Roubles.fromValue(50)));

        try {
            List<Banknote> list = atm.withdraw(100);
            assertNotNull(list);
            assertTrue(list.size() == 1);
            assertTrue(atm.balance() == 100);

        } catch (AtmException e) {
            fail();
        }
    }

}
