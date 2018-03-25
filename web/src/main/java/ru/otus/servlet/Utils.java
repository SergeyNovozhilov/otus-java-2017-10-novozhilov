package ru.otus.servlet;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

public class Utils {
    public static final String LOGIN_PARAMETER = "login";
    public static final String PASSWORD_PARAMETER = "password";

    public static final String MESSAGE_VARIABLE = "message";

    private static final String ADMIN_AUTHORIZATION = "YWRtaW46YWRtaW4=";

    public static final String LOGIN_PAGE = "login.html";
    public static final String ADMIN_PAGE = "admin.html";

    public static boolean checkAdmin(String login, String password) {
        String auth = login + ":" + password;
        String encoded = new String(Base64.getEncoder().encode(auth.getBytes()));
        return StringUtils.equals(ADMIN_AUTHORIZATION, encoded);
    }
}
