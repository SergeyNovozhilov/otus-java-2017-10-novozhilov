package ru.otus.web.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.database.DbService.DbService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminServlet extends HttpServlet {

    @Autowired
    private DbService db;


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
                pageVariables = createPageVariablesMap(null);
            } else {
                pageVariables = createPageVariablesMap("User " + requestLogin + " is not authorized to monitor data. ");
            }

            response.getWriter().println(TemplateProcessor.instance().getPage(LoginServlet.ADMIN_PAGE, pageVariables));
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
