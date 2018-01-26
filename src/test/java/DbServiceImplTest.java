//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import ru.otus.database.DataSet.AddressDataSet;
//import ru.otus.database.DataSet.PhoneDataSet;
//import ru.otus.database.DataSet.UserDataSet;
//import ru.otus.database.DbService.DbService;
//import ru.otus.database.DbService.DbServiceHibernateImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.apache.commons.collections4.CollectionUtils;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//public class DbServiceImplTest {
//    private DbService ds;
//
//    @Before
//    public void setUp() {
//        ds = new DbServiceHibernateImpl();
//    }
//
//    @Test
//    public void testDs() {
//        System.out.println("DbServiceImplTest : testDb");
//
//        String street = "Ark street";
//        AddressDataSet address = new AddressDataSet(street);
//        UserDataSet user = new UserDataSet("Jones", 27, address, null);
//        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet("110-12-23", user), new PhoneDataSet("113-23-34", user));
//        user.setPhones(phones);
//        ds.save(user);
//
//        long startTime = System.currentTimeMillis();
//        UserDataSet userRead1 = ds.load(1, UserDataSet.class);
//        long endTime = System.currentTimeMillis();
//
//        long duration1 = endTime - startTime;
//
//        System.out.println("duration1: " + duration1);
//
//        if (userRead1 == null) {
//            fail();
//        }
//
//        try {
//            Thread.sleep(1100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        startTime = System.currentTimeMillis();
//        UserDataSet userRead2 = ds.load(1, UserDataSet.class);
//        endTime = System.currentTimeMillis();
//
//        long duration2 = endTime - startTime;
//
//        if (userRead2 == null) {
//            fail();
//        }
//
//        System.out.println("duration2: " + duration2);
//
//        assertEquals(userRead1.getName(), userRead2.getName());
//        assertEquals(userRead1.getAge(), userRead2.getAge());
//        assertEquals(userRead1.getAddress().getStreet(), userRead2.getAddress().getStreet());
//
//        assertTrue(CollectionUtils.isEqualCollection(
//                userRead1.getPhones().stream().map(p -> p.getNumber()).collect(Collectors.toList()),
//                userRead2.getPhones().stream().map(p -> p.getNumber()).collect(Collectors.toList())));
//
//        assertTrue(duration1 < duration2);
//
//        long startNano = System.nanoTime();
//        UserDataSet userRead3 = ds.load(1, UserDataSet.class);
//        long endNano = System.nanoTime();
//
//        System.out.println("durationNano: " + (endNano - startNano));
//
//    }
//
//    @After
//    public void close () {
//        try {
//            ds.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
