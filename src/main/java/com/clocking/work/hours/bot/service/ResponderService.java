package com.clocking.work.hours.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ResponderService {
    SendMessage replyToMessage(Message message);
    SendMessage replyToAuth(Message message) throws GeneralSecurityException, IOException;
}
