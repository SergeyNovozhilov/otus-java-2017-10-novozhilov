import org.junit.Before;
import org.junit.Test;
import ru.otus.DataSet.AddressDataSet;
import ru.otus.DataSet.PhoneDataSet;
import ru.otus.DataSet.UserDataSet;
import ru.otus.DbService.DbService;
import ru.otus.DbService.DbServiceHibernateImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DbServiceImplTest {
    private DbService ds;

    @Before
    public void setUp() {
        ds = new DbServiceHibernateImpl();
    }

    @Test
    public void testDb() {
        System.out.println("DbServiceImplTest : testDb");
        UserDataSet data = new UserDataSet("Jackson", 18, new AddressDataSet("123-23-34"), new ArrayList<>());
        ds.save(data);
    }

    @Test
    public void testDbNullField() {
        System.out.println("DbServiceImplTest : testDbNullField");

    }


}
