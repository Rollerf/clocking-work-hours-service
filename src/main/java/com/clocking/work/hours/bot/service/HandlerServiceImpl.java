package com.clocking.work.hours.bot.service;

import com.clocking.work.hours.bot.entity.Token;
import com.clocking.work.hours.bot.error.BotException;
import com.clocking.work.hours.bot.error.ExceptionBotHandler;
import com.clocking.work.hours.googledrive.dto.DriveFiles;
import com.clocking.work.hours.googledrive.service.GoogleDriveService;
import com.clocking.work.hours.oauth.services.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.stream.Collectors;

@Service
public class HandlerServiceImpl implements HandlerService {

    private static final Logger logger = LoggerFactory.getLogger(HandlerServiceImpl.class);

    @Autowired
    ResponderService responderService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    GoogleDriveService googleDriveService;


    private ExceptionBotHandler exceptionBotHandler;

    @Override
    public SendMessage handleMessageUpdate(Update update)
            throws GeneralSecurityException, IOException, InterruptedException {
        Token token;
        Message message = update.getMessage();
        DriveFiles driveFiles;
        String bodyParametersForRefreshToken;

        if (message != null && message.hasText()) {
            logger.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());

            String text = message.getText();
            boolean isTemporalToken = text.length() == 62 && text.charAt(1) == '/';

            if("/login".equals(text)){
                return responderService.replyToAuth(message);
            }

            if (isTemporalToken) {
                bodyParametersForRefreshToken = authorizationService.getBodyParametersAccessToken(text);
                token = authorizationService.getRefreshAndAccessToken(bodyParametersForRefreshToken);
                driveFiles = googleDriveService.getDriveFiles(token.getAccessToken());
                message.setText(driveFiles.getFiles().stream().map(driveFile -> driveFile.getName() + "\n").collect(Collectors.joining()));

                return handleInputMessage(message);
            }
        }

        return handleInputMessage(message);
    }

    private SendMessage handleInputMessage(Message message) {
        try {
            return responderService.replyToMessage(message);
        } catch (BotException botException) {
            return exceptionBotHandler.handleExceptionResponse(message, botException);
        }
    }
}
