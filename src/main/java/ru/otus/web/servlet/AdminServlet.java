package ru.otus.web.servlet;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.app.DataObject;
import ru.otus.app.FrontendServiceMS;
import ru.otus.database.DataSet.AddressDataSet;
import ru.otus.database.DataSet.PhoneDataSet;
import ru.otus.database.DataSet.UserDataSet;
import ru.otus.database.DbService.DbService;
import ru.otus.database.DbService.MsgGetData;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Addressee;
import ru.otus.messageSystem.Message;
import ru.otus.messageSystem.MessageSystem;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
