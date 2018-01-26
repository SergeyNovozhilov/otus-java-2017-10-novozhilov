package ru.otus.web.servlet;

import ru.otus.cache.Cache;

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

    private Map<String, Object> createPageVariablesMap() {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("hit", cache.getHitCount());
        pageVariables.put("miss", cache.getMissCount());
        pageVariables.put("lifeTime", cache.getLifeTime());
        pageVariables.put("idleTime", cache.getIdleTime());
        pageVariables.put("max", cache.getMax());

        return pageVariables;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariablesMap();

        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
