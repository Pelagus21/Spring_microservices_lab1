package ua.com.kalinichev.microservices.core.exceptions;

public class IncorrectRequestBodyException extends RuntimeException {

    public IncorrectRequestBodyException(String message) {
        super(message);
    }
}
