package com.clocking.work.hours.bot.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerService {
    SendMessage handleMessageUpdate(Update update);
}
