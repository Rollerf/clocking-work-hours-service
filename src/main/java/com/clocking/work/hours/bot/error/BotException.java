package com.clocking.work.hours.bot.error;

public abstract class BotException extends RuntimeException {
    public abstract String getExceptionMessageSource();
}
