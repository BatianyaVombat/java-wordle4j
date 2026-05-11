package ru.yandex.practicum;

public class InvalidWordException extends RuntimeException {
    public InvalidWordException(String message) {
        super(message);
    }

    public InvalidWordException(String message, Throwable cause) {
        super(message, cause);
    }
}
