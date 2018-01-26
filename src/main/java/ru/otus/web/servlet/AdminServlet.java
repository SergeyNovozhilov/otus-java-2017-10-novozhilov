package ru.otus.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;


public class AdminServlet extends HttpServlet {

    private static final String ADMIN_PAGE = "admin.html";

    private final Cache cache;

    public AdminServlet(Cache cache) {
        this.cache = cache;
    }

    private static Map<String, Object> createPageVariablesMap() {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("hit", cache.getHit());
        pageVariables.put("miss", cache.getMiss());
        pageVariables.put("lifeTime", cache.getLifeTime());
        pageVariables.put("idleTime", cache.getIdleTime());


        return pageVariables;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap();

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
