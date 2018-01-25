package ru.otus.web.servlet;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
    private static final String LOGIN_PAGE = "login.html";
    private static final String ADMIN_PAGE = "admin.html";


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
        String message = "";

        String requestLogin = request.getParameter(LOGIN_PARAMETER);
        String requestPassword = request.getParameter(PASSWORD_PARAMETER);

        if (requestLogin != null) {


            String auth = requestLogin + ":" + requestPassword;

            String encoded = new String(Base64.getEncoder().encode(auth.getBytes()));

            if (StringUtils.equals(ADMIN_AUTHORIZATION, encoded)) {
                pageName = ADMIN_PAGE;
            } else {
                message = "User " + requestLogin + " is not authorized to admin page";
            }


            if (requestLogin != null) {
//            saveToVariable(requestLogin);
                saveToSession(request, requestLogin); //request.getSession().getAttribute("login");
                saveToServlet(request, requestLogin); //request.getAttribute("login");
                saveToCookie(response, requestLogin); //request.getCookies();
            }

        }

        String page = getPage(pageName, message); //save to the page
        response.getWriter().println(page);

        setOK(response);
    }

    private void saveToCookie(HttpServletResponse response, String requestLogin) {
        response.addCookie(new Cookie("L12.1-login", requestLogin));
    }

    private void saveToServlet(HttpServletRequest request, String requestLogin) {
        request.getServletContext().setAttribute("login", requestLogin);
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }

//    private void saveToVariable(String requestLogin) {
//        login = requestLogin != null ? requestLogin : login;
//    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
