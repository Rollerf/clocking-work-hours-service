package com.clocking.work.hours.bot;

import com.clocking.work.hours.bot.config.BotProperties;
import com.clocking.work.hours.bot.service.HandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class TelegramBotManagement extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotManagement.class);

    @Autowired
    private BotProperties botProperties;

    @Autowired
    HandlerService handlerService;

    @Override
    public void onUpdateReceived(Update update){
        SendMessage response = null;
        try {
            response = handlerService.handleMessageUpdate(update);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            logger.error("Error getting the refresh token");
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        sendMessage(response);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getBotName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", botProperties.getBotName(), botProperties.getToken());
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("Error sending message {}", e.getMessage());
        }
    }
}
