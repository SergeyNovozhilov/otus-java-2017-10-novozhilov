import org.junit.Before;
import org.junit.Test;
import ru.otus.DataSet.AddressDataSet;
import ru.otus.DataSet.PhoneDataSet;
import ru.otus.DataSet.UserDataSet;
import ru.otus.DbService.DbService;
import ru.otus.DbService.DbServiceHibernateImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

        AddressDataSet address = new AddressDataSet("Ark street");
        UserDataSet user = new UserDataSet("Jones", 27, address, null);
        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet("110-12-23", user), new PhoneDataSet("113-23-34", user));
        user.setPhones(phones);
        ds.save(user);

        UserDataSet userRead = ds.load(1, UserDataSet.class);

        if (userRead == null) {
            fail();
        }

        assertEquals(userRead.getName(), user.getName());
        assertEquals(userRead.getAge(), user.getAge());
        assertEquals(userRead.getAddress().getStreet(), user.getAddress().getStreet());

        assertTrue(CollectionUtils.isEqualCollection(
                userRead.getPhones().stream().map(p -> p.getNumber()).collect(Collectors.toList()),
                user.getPhones().stream().map(p -> p.getNumber()).collect(Collectors.toList())));

        AddressDataSet addressRead = ds.load(1, AddressDataSet.class);

        if (addressRead == null) {
            fail();
        }

        assertEquals(addressRead.getStreet(), address.getStreet());

    }

}
