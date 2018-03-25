package ru.otus.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class AdminServlet
    extends
 HttpServlet {
    private Logger logger = Logger.getLogger(this.getClass().getName());


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestLogin = (String) request.getSession().getAttribute(Utils.LOGIN_PARAMETER);
        String requestPassword = (String) request.getSession().getAttribute(Utils.PASSWORD_PARAMETER);

        if (requestLogin == null || requestPassword == null) {
            response.getWriter().println(TemplateProcessor.instance().getPage(Utils.LOGIN_PAGE, null));
        } else {
            if (Utils.checkAdmin(requestLogin, requestPassword)) {
                RequestDispatcher view = request.getRequestDispatcher("admin.html");
                view.forward(request, response);
            } else {
                String message = "User " + requestLogin + " is not authorized to monitor data. ";
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put(Utils.MESSAGE_VARIABLE, message);
                response.getWriter().println(TemplateProcessor.instance().getPage(Utils.LOGIN_PAGE, pageVariables));
            }
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
