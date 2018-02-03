package ru.otus.web.servlet;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER = "login";
    public static final String PASSWORD_PARAMETER = "password";

    private static final String ADMIN_AUTHORIZATION = "YWRtaW46YWRtaW4=";

    private static final String MESSAGE_VARIABLE = "message";
    public static final String LOGIN_PAGE = "login.html";
    public static final String ADMIN_PAGE = "admin.html";


    private static String getPage(String page, String message) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(MESSAGE_VARIABLE, message);
        return TemplateProcessor.instance().getPage(page, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String pageName = LOGIN_PAGE;
        String message = null;

        String requestLogin = request.getParameter(LOGIN_PARAMETER);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER);

        if (requestLogin != null && requestPassword != null) {

            if (checkAdmin(requestLogin, requestPassword)) {
                pageName = ADMIN_PAGE;
            } else {
                message = "User " + requestLogin + " is not authorized to admin page";
            }
            saveToSession(request, requestLogin, requestPassword);
        }

        String page = getPage(pageName, message);
        response.getWriter().println(page);

        setOK(response);
    }

    public static boolean checkAdmin(String login, String password) {
        String auth = login + ":" + password;
        String encoded = new String(Base64.getEncoder().encode(auth.getBytes()));
        return StringUtils.equals(ADMIN_AUTHORIZATION, encoded);
    }

    private void saveToSession(HttpServletRequest request, String login, String password) {
        request.getSession().setAttribute("login", login);
        request.getSession().setAttribute("password", password);
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
