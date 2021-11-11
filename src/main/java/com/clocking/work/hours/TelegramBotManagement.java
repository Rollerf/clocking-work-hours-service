package com.clocking.work.hours;

import com.clocking.work.hours.domain.BotProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class TelegramBotManagement extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotManagement.class);

    @Autowired
    private BotProperties botProperties;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(String.valueOf(chatId));
            String text = message.getText();
            response.setText(text);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
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
}
