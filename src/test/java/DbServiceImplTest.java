import org.junit.Before;
import org.junit.Test;
import ru.otus.DataSet.UserDataSet;
import ru.otus.DbConnection.PostgresConnection;
import ru.otus.DbService.DbService;
import ru.otus.DbService.DbServiceHibernateImpl;
import ru.otus.DbService.DbServiceImpl;
import ru.otus.ResultMapper.UserMapper;

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

    }

    @Test
    public void testDbNullField() {
        System.out.println("DbServiceImplTest : testDbNullField");

    }


}
