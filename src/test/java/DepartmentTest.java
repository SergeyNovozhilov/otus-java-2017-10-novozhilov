import org.junit.Before;
import org.junit.Test;
import ru.otus.Atm.Atm;
import ru.otus.AtmImpl.AtmException;
import ru.otus.Banknote.Banknote;
import ru.otus.BanknoteImpl.Roubles;
import ru.otus.Department.Department;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DepartmentTest {
    private Department department;

    @Before
    public void setUp() {
        department = new Department();
    }

    @Test
    public void createOneAtmTest(){
        System.out.println("DepartmentTest: createOneAtmTest");
        Atm atm = department.addAtm(Roubles.fromValue(10), 10);
        assertNotNull(atm);
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
        Atm atm1 = department.addAtm(Roubles.fromValue(10), 10);
        Atm atm2 = department.addAtm(Roubles.fromValue(10));
        assertNotNull(atm1);
        assertNotNull(atm2);
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
        Atm atm1 = department.addAtm(Roubles.fromValue(10), 10);
        Atm atm2 = department.addAtm(Roubles.fromValue(10));
        assertNotNull(atm1);
        assertNotNull(atm2);
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
        department.unregister(atm1);
        department.restoreInitial();

        assertTrue(bal1 != atm1.balance());
        assertTrue(bal2 == atm2.balance());
    }
}
