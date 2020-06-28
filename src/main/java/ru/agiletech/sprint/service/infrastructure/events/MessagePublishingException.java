package ru.agiletech.sprint.service.infrastructure.events;

public class MessagePublishingException extends RuntimeException{

    public MessagePublishingException() {
    }

    public MessagePublishingException(String message) {
        super(message);
    }

    public MessagePublishingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagePublishingException(Throwable cause) {
        super(cause);
    }

    public MessagePublishingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
