package ua.com.kalinichev.microservices.core.exceptions;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
