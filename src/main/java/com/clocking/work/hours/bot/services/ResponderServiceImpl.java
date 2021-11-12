package com.clocking.work.hours.bot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ResponderServiceImpl implements ResponderService {
    public SendMessage replyToMessage(Message message) {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        response.setChatId(String.valueOf(chatId));
        String text = message.getText();
        response.setText(text);

        return response;
    }
}
