package com.clocking.work.hours.googledrive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GoogleDriveConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
