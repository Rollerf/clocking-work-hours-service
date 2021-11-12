package com.clocking.work.hours.bot.services;

import com.clocking.work.hours.bot.error.BotException;
import com.clocking.work.hours.bot.error.ExceptionBotHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HandlerServiceImpl implements HandlerService {

    private static final Logger logger = LoggerFactory.getLogger(HandlerServiceImpl.class);

    @Autowired
    ResponderService responderService;

    private ExceptionBotHandler exceptionBotHandler;

    @Override
    public SendMessage handleMessageUpdate(Update update) {
        SendMessage replyMessage = null;

        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            logger.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());

            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        try {
            return responderService.replyToMessage(message);
        } catch (BotException botException) {
            return exceptionBotHandler.handleExceptionResponse(message, botException);
        }
    }
}
