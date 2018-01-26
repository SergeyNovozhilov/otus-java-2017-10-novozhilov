import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.database.DataSet.AddressDataSet;
import ru.otus.database.DataSet.PhoneDataSet;
import ru.otus.database.DataSet.UserDataSet;
import ru.otus.database.DbService.DbService;
import ru.otus.database.DbService.DbServiceHibernateImpl;
import ru.otus.web.servlet.AdminServlet;
import ru.otus.web.servlet.LoginServlet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WebTest {
    private DbService ds;
    private Server server;

    private final int PORT = 8092;
    private final String PUBLIC = "public";

    @Before
    public void setUp(){
        ds = new DbServiceHibernateImpl();

        String street = "Ark street";
        AddressDataSet address = new AddressDataSet(street);
        UserDataSet user = new UserDataSet("Jones", 27, address, null);
        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet("110-12-23", user), new PhoneDataSet("113-23-34", user));
        user.setPhones(phones);
        ds.save(user);
        UserDataSet userRead1 = ds.load(1, UserDataSet.class);

        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserDataSet userRead2 = ds.load(1, UserDataSet.class);
        UserDataSet userRead3 = ds.load(1, UserDataSet.class);

    }

    @Test
    public void testServer() throws Exception{
        System.out.println("WebTest : testServer");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC);


        context.addServlet(new ServletHolder(new AdminServlet(ds.getCache())), "/admin");
        context.addServlet(LoginServlet.class, "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();

    }

    @After
    public void close () {
        try {
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
