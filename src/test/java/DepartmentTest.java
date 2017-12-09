import org.junit.Before;
import org.junit.Test;
import ru.otus.Atm.Atm;
import ru.otus.AtmImpl.AtmException;
import ru.otus.AtmImpl.AtmImpl;
import ru.otus.BanknoteImpl.Roubles;
import ru.otus.Department.Department;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DepartmentTest {
    private Department department;

    @Before
    public void setUp() {
        department = new Department();
    }

    @Test
    public void createOneAtmTest(){
        System.out.println("DepartmentTest: createOneAtmTest");
        Atm atm = new AtmImpl();
        assertNotNull(atm);
        assertTrue(department.addAtm(atm));
        atm.startAtm(Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100)), 10);
        assertTrue(department.getTotalBalance() == 1500);
        boolean result = department.register(atm);
        assertTrue(result);
        int bal = atm.balance();
        try {
            atm.withdraw(bal);
        } catch (AtmException e) {
            fail();
        }
        assertTrue(atm.balance() == 0);
        department.restoreInitial();
        assertTrue(bal == atm.balance());
    }

    @Test
    public void createTwoAtmTest(){
        System.out.println("DepartmentTest: createTwoAtmTest");

        Atm atm1 = new AtmImpl();
        assertNotNull(atm1);
        assertTrue(department.addAtm(atm1));
        atm1.startAtm(Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100)), 10);
        assertTrue(department.getTotalBalance() == 1500);
        Atm atm2 = new AtmImpl();
        assertNotNull(atm2);
        assertTrue(department.addAtm(atm2));
        atm2.startAtm(Arrays.asList(Roubles.fromValue(50)), 0);

        assertTrue(department.getTotalBalance() == 1500);

        department.register(atm1);
        department.register(atm2);
        int bal1 = atm1.balance();
        int bal2 = atm2.balance();
        try {
            atm1.withdraw(bal1);
            atm2.withdraw(bal2);
        } catch (AtmException e) {
            fail();
        }
        department.restoreInitial();

        assertTrue(bal1 == atm1.balance());
        assertTrue(bal2 == atm2.balance());
    }

    @Test
    public void createAtmTest(){
        System.out.println("DepartmentTest: createTwoAtmTest");
        Atm atm1 = new AtmImpl();
        assertNotNull(atm1);
        assertTrue(department.addAtm(atm1));
        atm1.startAtm(Arrays.asList(Roubles.fromValue(50), Roubles.fromValue(100)), 10);
        Atm atm2 = new AtmImpl();
        assertNotNull(atm2);
        assertTrue(department.addAtm(atm2));
        atm2.startAtm(Arrays.asList(Roubles.fromValue(50)), 0);

        department.register(atm1);
        department.register(atm2);
        int bal1 = atm1.balance();
        int bal2 = atm2.balance();
        try {
            atm1.withdraw(bal1);
            atm2.withdraw(bal2);
            assertTrue(department.getTotalBalance() == 0);
        } catch (AtmException e) {
            fail();
        }
        department.unregister(atm1);
        department.restoreInitial();

        assertTrue(bal1 != atm1.balance());
        assertTrue(bal2 == atm2.balance());
    }
}
