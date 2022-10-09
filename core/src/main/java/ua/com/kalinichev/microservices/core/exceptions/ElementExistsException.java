package ua.com.kalinichev.microservices.core.exceptions;

public class ElementExistsException extends RuntimeException {
    public ElementExistsException(String message) {
        super(message);
    }
}
