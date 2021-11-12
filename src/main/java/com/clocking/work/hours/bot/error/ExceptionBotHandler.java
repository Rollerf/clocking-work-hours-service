package com.clocking.work.hours.bot.error;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Locale;

@Component
public class ExceptionBotHandler {
    private final MessageSource messageSource;

    public ExceptionBotHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public SendMessage handleExceptionResponse(Message message, BotException exception) {
        String messageException = exception.getExceptionMessageSource();
        String messageText = messageSource.getMessage(messageException, null, Locale.getDefault());
        SendMessage response = new SendMessage();

        response.setChatId(message.getChatId().toString());
        response.setText(messageText);

        return response;
    }
}
