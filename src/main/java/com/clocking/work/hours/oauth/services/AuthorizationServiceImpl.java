package com.clocking.work.hours.oauth.services;

import com.clocking.work.hours.bot.entity.Token;
import com.clocking.work.hours.bot.util.Constants;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Value("${auth.credentials}")
    private String credentialsFilePath;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    public String getUrlAuthorization() throws IOException {
        StringBuilder urlAuthorization = new StringBuilder();
        InputStream in = AuthorizationServiceImpl.class.getResourceAsStream(credentialsFilePath);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleClientSecrets.Details credentialsJson = clientSecrets.getDetails();
        List<String> lstRedirectUris = credentialsJson.getRedirectUris();

        urlAuthorization.append(credentialsJson.getAuthUri());
        urlAuthorization.append(Constants.INTERROGATION_SYMBOL);
        urlAuthorization.append(Constants.ACCESS_TYPE_OFFLINE);
        urlAuthorization.append(Constants.AND_SYMBOL);

        urlAuthorization.append(Constants.CLIENT_ID);
        urlAuthorization.append(Constants.EQUALS_SYMBOL);
        urlAuthorization.append(credentialsJson.getClientId());
        urlAuthorization.append(Constants.AND_SYMBOL);

        urlAuthorization.append(Constants.REDIRECT_URI);
        urlAuthorization.append(Constants.EQUALS_SYMBOL);
        urlAuthorization.append(lstRedirectUris.get(0));
        urlAuthorization.append(Constants.AND_SYMBOL);

        urlAuthorization.append(Constants.RESPONSE_TYPE);
        urlAuthorization.append(Constants.EQUALS_SYMBOL);
        urlAuthorization.append(Constants.CODE);
        urlAuthorization.append(Constants.AND_SYMBOL);

        urlAuthorization.append(Constants.SCOPE);
        urlAuthorization.append(Constants.EQUALS_SYMBOL);
        urlAuthorization.append(SCOPES.get(0));

        return urlAuthorization.toString();
    }

    public Token getRefreshAndAccessToken(String accessTokenParameters) throws IOException, InterruptedException {
        Gson gson = new Gson();
        Token token;
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constants.REFRESH_TOKEN_ENDPOINT))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(accessTokenParameters))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        token = gson.fromJson(response.body(), Token.class);

        return token;
    }

    public String getBodyParametersAccessToken(String temporaryToken) throws IOException {
        StringBuilder accessTokenParameters = new StringBuilder();
        InputStream in = AuthorizationServiceImpl.class.getResourceAsStream(credentialsFilePath);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleClientSecrets.Details credentialsJson = clientSecrets.getDetails();
        List<String> lstRedirectUris = credentialsJson.getRedirectUris();

        accessTokenParameters.append(Constants.CODE);
        accessTokenParameters.append(Constants.EQUALS_SYMBOL);
        accessTokenParameters.append(temporaryToken);
        accessTokenParameters.append(Constants.AND_SYMBOL);

        accessTokenParameters.append(Constants.CLIENT_ID);
        accessTokenParameters.append(Constants.EQUALS_SYMBOL);
        accessTokenParameters.append(credentialsJson.getClientId());
        accessTokenParameters.append(Constants.AND_SYMBOL);

        accessTokenParameters.append(Constants.CLIENT_SECRET);
        accessTokenParameters.append(Constants.EQUALS_SYMBOL);
        accessTokenParameters.append(credentialsJson.getClientSecret());
        accessTokenParameters.append(Constants.AND_SYMBOL);

        accessTokenParameters.append(Constants.REDIRECT_URI);
        accessTokenParameters.append(Constants.EQUALS_SYMBOL);
        accessTokenParameters.append(lstRedirectUris.get(0));
        accessTokenParameters.append(Constants.AND_SYMBOL);

        accessTokenParameters.append(Constants.GRANT_TYPE);
        accessTokenParameters.append(Constants.EQUALS_SYMBOL);
        accessTokenParameters.append(Constants.AUTHORIZATION_CODE);

        return accessTokenParameters.toString();
    }
}
