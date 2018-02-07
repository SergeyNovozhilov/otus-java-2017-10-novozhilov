package ru.otus.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final String MESSAGE_VARIABLE = "message";


    private static String getPage(String page, String message) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(MESSAGE_VARIABLE, message);
        return TemplateProcessor.instance().getPage(page, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String pageName = Utils.LOGIN_PAGE;
        String message = null;

        String requestLogin = request.getParameter(Utils.LOGIN_PARAMETER);
        String requestPassword = request.getParameter(Utils.PASSWORD_PARAMETER);

        if (requestLogin != null && requestPassword != null) {

            if (Utils.checkAdmin(requestLogin, requestPassword)) {
                pageName = Utils.ADMIN_PAGE;
            } else {
                message = "User " + requestLogin + " is not authorized to admin page";
            }
            saveToSession(request, requestLogin, requestPassword);
        }

        String page = getPage(pageName, message);
        response.getWriter().println(page);

        setOK(response);
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
