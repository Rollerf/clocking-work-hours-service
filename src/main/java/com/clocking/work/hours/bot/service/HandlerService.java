package com.clocking.work.hours.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface HandlerService {
    SendMessage handleMessageUpdate(Update update) throws GeneralSecurityException, IOException, InterruptedException;
}
