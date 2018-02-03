package ru.otus.web.servlet;

import ru.otus.cache.Cache;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AdminServlet extends HttpServlet {

    private final Cache cache;

    public AdminServlet(Cache cache) {
        this.cache = cache;
    }

    private Map<String, Object> createPageVariablesMap(String message) {
        Map<String, Object> pageVariables = new HashMap<>();
        if (message == null) {
            pageVariables.put("hit", cache.getHitCount());
            pageVariables.put("miss", cache.getMissCount());
            pageVariables.put("lifeTime", cache.getLifeTime());
            pageVariables.put("idleTime", cache.getIdleTime());
            pageVariables.put("max", cache.getMax());
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
