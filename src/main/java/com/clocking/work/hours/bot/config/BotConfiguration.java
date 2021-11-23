package com.clocking.work.hours.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class BotConfiguration {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        Locale.setDefault(Locale.US);

        var source = new ResourceBundleMessageSource();
        source.setBasenames("messages/replies");
        source.setDefaultEncoding("UTF-8");

        return source;
    }

    @Bean
    public List<Locale> supportedLocales(@Value("tgbot.supportedLanguages") String supportedLanguagesStr) {
        return Arrays.stream(Optional.ofNullable(supportedLanguagesStr).map(s -> s.split(",")).orElse(new String[]{Locale.US.getLanguage()}))
                .map(Locale::new).collect(Collectors.toList());
    }
}
