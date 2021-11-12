package com.clocking.work.hours.bot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@PropertySource(
        value = "classpath:bot-credentials.json",
        factory = JsonPropertySourceFactory.class
)
@ConfigurationProperties
public class BotProperties {
    private String token;
    private String botName;
}
