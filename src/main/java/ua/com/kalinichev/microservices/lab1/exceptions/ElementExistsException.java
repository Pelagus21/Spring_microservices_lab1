package ua.com.kalinichev.microservices.lab1.exceptions;

public class ElementExistsException extends RuntimeException {
    public ElementExistsException(String message) {
        super(message);
    }
}
