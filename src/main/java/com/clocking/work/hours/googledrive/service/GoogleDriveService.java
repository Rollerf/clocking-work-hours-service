package com.clocking.work.hours.googledrive.service;

import com.clocking.work.hours.googledrive.dto.DriveFiles;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleDriveService {

    @Autowired
    private RestTemplate restTemplate;

    public DriveFiles getDriveFiles(String accessToken) {
        String requestUri = "https://www.googleapis.com/drive/v3/files";
        HttpHeaders headers = new HttpHeaders();
        Gson gson = new Gson();
        HttpEntity<String> request;
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
        ResponseEntity<String> response;

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        request = new HttpEntity <> (headers);
        mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);

        return gson.fromJson(response.getBody(), DriveFiles.class);
    }
}