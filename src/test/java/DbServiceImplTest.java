import org.junit.Before;
import org.junit.Test;
import ru.otus.DataSet.UserDataSet;
import ru.otus.DbConnectionHelper.PostgresConnectionHelper;
import ru.otus.DbService.DbServiceImpl;
import ru.otus.ResultMapper.UserMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DbServiceImplTest {
    private DbServiceImpl ds;

    @Before
    public void setUp() {
        ds = new DbServiceImpl(new PostgresConnectionHelper());
        ds.dropTable(UserDataSet.class);
        ds.register(UserDataSet.class, new UserMapper());
    }

    @Test
    public void testDb() {
        System.out.println("DbServiceImplTest : testDb");
        if (ds == null) {
            fail();
        }

        UserDataSet userOrig = new UserDataSet("Jack", 23);

        ds.save(userOrig);

        UserDataSet user = ds.load(1, UserDataSet.class);


        assertEquals(userOrig.getName(), user.getName());
        assertEquals(userOrig.getAge(), user.getAge());

    }

    @Test
    public void testDbNullField() {
        System.out.println("DbServiceImplTest : testDbNullField");
        if (ds == null) {
            fail();
        }
        String nul = "null";

        UserDataSet userOrig = new UserDataSet(nul, 23);

        ds.save(userOrig);

        UserDataSet user = ds.load(1, UserDataSet.class);


        assertEquals(userOrig.getName(), user.getName());
        assertEquals(userOrig.getAge(), user.getAge());

    }


}
