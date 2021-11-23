package com.clocking.work.hours.oauth.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public String getUrlAuthorization() throws IOException {
        StringBuilder credentials = new StringBuilder();
        // Load client secrets.
        InputStream in = AuthorizationServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleClientSecrets.Details credentialsJson = clientSecrets.getDetails();
        List<String> lstRedirectUris = credentialsJson.getRedirectUris();

        credentials.append(credentialsJson.getAuthUri());
        credentials.append("?");
        credentials.append("access_type=offline");
        credentials.append("&");
        credentials.append("client_id");
        credentials.append("=");
        credentials.append(credentialsJson.getClientId());
        credentials.append("&");
        credentials.append("redirect_uri");
        credentials.append("=");
        credentials.append(lstRedirectUris.get(0));
        credentials.append("&");
        credentials.append("response_type");
        credentials.append("=");
        credentials.append("code");
        credentials.append("&");
        credentials.append("scope");
        credentials.append("=");
        credentials.append(SCOPES.get(0));

        return credentials.toString();
    }

    private String generateCodeVerifier(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }
    private String generateCodeChallange(String codeVerifier) throws NoSuchAlgorithmException {
        byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bytes, 0, bytes.length);
        byte[] digest = messageDigest.digest();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
