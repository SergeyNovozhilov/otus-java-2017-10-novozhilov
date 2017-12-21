import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.SimpleJson.SimpleJson;
import ru.otus.person.Address;
import ru.otus.person.Person;
import ru.otus.person.PhoneNumber;
import ru.otus.person.SomeClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleJsonTest {

    private Person person;

    @Before
    public void setUp() {
        person = new Person();
        person.setAddress(new Address("Street", 12345, "State", "City"));
        person.setAge(25);
        person.setFirstName(null);
        person.setLastName("Smith");
        person.setPhoneNumber(new PhoneNumber[]{new PhoneNumber("123 12-131-12", "phone"),
                new PhoneNumber("123 12-131-13", "fax"), null});

        person.setStrings(Arrays.asList("one", "two"));

        Set<SomeClass> set = new HashSet<>();
        String[] array = {"aa", "bb"};

        SomeClass some1 = new SomeClass();
        some1.setArray(array);
        set.add(some1);

        SomeClass some2 = new SomeClass();
        some2.setArray(array);
        set.add(some2);

        person.setSet(null);
        String[] arr = {"AAA", "BBB"};
        person.setArray(arr);
    }

    @Test
    public void testSimpleJson() {
        System.out.println("SimpleJsonTest: testSimpleJson");

        SimpleJson sj = new SimpleJson();

        String string = sj.toJson(person);

        Gson gson = new Gson();

        Person person1 = gson.fromJson(string, Person.class);


        assertEquals(person, person1);
    }

    @Test
    public void test() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(123));
        System.out.println(gson.toJson("abc"));
        System.out.println(gson.toJson(true));

        SimpleJson sj = new SimpleJson();
        System.out.println(sj.toJson("abc"));
        System.out.println(sj.toJson(123));

        System.out.println(sj.toJson(true));
    }
}
