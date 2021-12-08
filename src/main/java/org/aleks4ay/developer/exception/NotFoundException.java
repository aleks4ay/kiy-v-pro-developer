package org.aleks4ay.developer.exception;

public class NotFoundException extends Exception{
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message + super.toString();
    }
}
