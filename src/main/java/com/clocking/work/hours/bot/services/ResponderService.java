package com.clocking.work.hours.bot.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ResponderService {
    SendMessage replyToMessage(Message message);
}
