package ru.otus.web.servlet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.app.FrontendServiceMS;
import ru.otus.messageSystem.MessageSystem;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ServerEndpoint("/admin")
public class AdminWebSocketEndPoint {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private MessageSystem ms;

    private final BiMap<FrontendServiceMS, Session> frontSesionMap;

    public AdminWebSocketEndPoint() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        frontSesionMap = HashBiMap.create();
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected: " + session.getId());
        FrontendServiceMS front = new FrontendServiceMsImpl(ms, this);
        ms.register(front);
        frontSesionMap.put(front, session);
    }

    @OnMessage
    public void onMessage(String text, Session session) {
        logger.info("On message. Session: " + session.getId());
        FrontendServiceMS front = frontSesionMap.inverse().get(session);
        if (front != null) {
            front.handleRequest();
        } else {
            logger.warning("front  for " + session.getId() + "not found");
        }
    }

    public void sendMessage(String text, FrontendServiceMS front) {
        Session session = frontSesionMap.get(front);
        if (session != null) {
            logger.info("Sending: " + text);
            try {
                session.getBasicRemote().sendText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.warning("session  for " + front.getName() + "not found");
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        FrontendServiceMS front = frontSesionMap.inverse().get(session);
        if (front != null) {
            frontSesionMap.remove(front);
            ms.unregister(front.getAddress());
        } else {
            logger.warning("front  for " + session.getId() + "not found");
        }
    }
}
