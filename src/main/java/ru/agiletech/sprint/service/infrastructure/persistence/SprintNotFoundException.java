package ru.agiletech.sprint.service.infrastructure.persistence;

public class SprintNotFoundException extends RuntimeException{

    public SprintNotFoundException() {
    }

    public SprintNotFoundException(String message) {
        super(message);
    }

    public SprintNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SprintNotFoundException(Throwable cause) {
        super(cause);
    }

    public SprintNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
