package com.clocking.work.hours.oauth.services;

import com.clocking.work.hours.bot.entity.Token;

import java.io.IOException;

public interface AuthorizationService {
    String getUrlAuthorization() throws IOException;
    Token getRefreshAndAccessToken(String temporaryToken) throws InterruptedException, IOException;
    String getBodyParametersAccessToken(String temporaryToken) throws IOException;
}
