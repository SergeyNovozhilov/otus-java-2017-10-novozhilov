package ru.otus.web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.database.DataSet.AddressDataSet;
import ru.otus.database.DataSet.PhoneDataSet;
import ru.otus.database.DataSet.UserDataSet;
import ru.otus.database.DbService.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private final DbService db;

    @Autowired
    public AdminServlet(DbService db) {
        this.db = db;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private Map<String, Object> createPageVariablesMap(String message) {
        Map<String, Object> pageVariables = new HashMap<>();
        if (message == null) {
            pageVariables.put("hit", db.getCache().getHitCount());
            pageVariables.put("miss", db.getCache().getMissCount());
            pageVariables.put("lifeTime", db.getCache().getLifeTime());
            pageVariables.put("idleTime", db.getCache().getIdleTime());
            pageVariables.put("max", db.getCache().getMax());
        } else {
            pageVariables.put("message", message);
        }
        return pageVariables;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestLogin = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER);
        String requestPassword = (String) request.getSession().getAttribute(LoginServlet.PASSWORD_PARAMETER);

        if (requestLogin == null || requestPassword == null) {
            response.getWriter().println(TemplateProcessor.instance().getPage(LoginServlet.LOGIN_PAGE, null));
        } else {
            Map<String, Object> pageVariables;
            if (LoginServlet.checkAdmin(requestLogin, requestPassword)) {
                sebDb(db);
                pageVariables = createPageVariablesMap(null);
            } else {
                pageVariables = createPageVariablesMap("User " + requestLogin + " is not authorized to monitor data. ");
            }

            response.getWriter().println(TemplateProcessor.instance().getPage(LoginServlet.ADMIN_PAGE, pageVariables));
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void sebDb(DbService ds) {
        System.out.println("setDb");

        String street = "Ark street";
        AddressDataSet address = new AddressDataSet(street);
        UserDataSet user = new UserDataSet("Jones", 27, address, null);
        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet("110-12-23", user), new PhoneDataSet("113-23-34", user));
        user.setPhones(phones);
        ds.save(user);
        UserDataSet userRead1 = ds.load(1, UserDataSet.class);
        if (userRead1 == null) {
            System.out.println("Error! userRead1 is null");
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserDataSet userRead2 = ds.load(1, UserDataSet.class);
        if (userRead2 == null) {
            System.out.println("Error! userRead2 is null");
        }
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserDataSet userRead3 = ds.load(1, UserDataSet.class);
        if (userRead3 == null) {
            System.out.println("Error! userRead3 is null");
        }
    }
}
