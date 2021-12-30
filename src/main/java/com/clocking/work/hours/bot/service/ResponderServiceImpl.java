package com.clocking.work.hours.bot.service;

import com.clocking.work.hours.oauth.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

@Service
public class ResponderServiceImpl implements ResponderService {
    @Autowired
    AuthorizationService authorizationService;

    public SendMessage replyToMessage(Message message) {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        response.setChatId(String.valueOf(chatId));
        String text = message.getText();
        response.setText(text);

        return response;
    }

    public SendMessage replyToAuth(Message message) throws IOException {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        response.setChatId(String.valueOf(chatId));
        String text = authorizationService.getUrlAuthorization();

        response.setText(text);

        return response;
    }
}
