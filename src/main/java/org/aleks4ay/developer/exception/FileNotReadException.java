package org.aleks4ay.developer.exception;

import java.io.IOException;

public class FileNotReadException extends IOException{
    private final String fileName;

    public FileNotReadException(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Can't read data from file '" + fileName + "'. " + super.toString();
    }
}
