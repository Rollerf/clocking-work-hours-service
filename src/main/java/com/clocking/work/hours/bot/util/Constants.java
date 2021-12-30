package com.clocking.work.hours.bot.util;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    //AUTH
    public static final String CODE = "code";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String GRANT_TYPE = "grant_type";
    public static final String ACCESS_TYPE_OFFLINE = "access_type=offline";
    public static final String SCOPE = "scope";
    public static final String RESPONSE_TYPE = "response_type";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String REFRESH_TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    //SYMBOLS
    public static final String INTERROGATION_SYMBOL = "?";
    public static final String EQUALS_SYMBOL = "=";
    public static final String AND_SYMBOL = "&";
    //BOT COMMANDS
    public static final String LOGIN = "/login";
}
