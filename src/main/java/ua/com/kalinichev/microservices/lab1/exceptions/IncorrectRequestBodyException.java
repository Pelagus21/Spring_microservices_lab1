package ua.com.kalinichev.microservices.lab1.exceptions;

public class IncorrectRequestBodyException extends RuntimeException {

    public IncorrectRequestBodyException(String message) {
        super(message);
    }
}
