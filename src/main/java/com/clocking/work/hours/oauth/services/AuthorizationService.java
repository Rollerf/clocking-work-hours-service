package com.clocking.work.hours.oauth.services;

import java.io.IOException;

public interface AuthorizationService {
    String getUrlAuthorization() throws IOException;
}
