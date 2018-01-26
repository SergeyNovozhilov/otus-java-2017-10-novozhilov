package ru.otus.web;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.database.DbService.DbService;
import ru.otus.database.DbService.DbServiceHibernateImpl;
import ru.otus.web.servlet.AdminServlet;
import ru.otus.web.servlet.LoginServlet;

public class Main {

    private final static int PORT = 8092;
    private final static String PUBLIC = "public";
    public static void main(String[] args) throws Exception{

//        DbService ds = new DbServiceHibernateImpl();
//
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase(PUBLIC);
//
//
//        context.addServlet(new ServletHolder(new AdminServlet(ds.getCache())), "/admin");
//        context.addServlet(LoginServlet.class, "/login");
//
//        Server server = new Server(PORT);
//        server.setHandler(new HandlerList(resourceHandler, context));
//
//        server.start();
//        server.join();
    }

}
