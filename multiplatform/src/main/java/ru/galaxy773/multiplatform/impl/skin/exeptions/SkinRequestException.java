package ru.galaxy773.multiplatform.impl.skin.exeptions;

public class SkinRequestException extends Exception {

    public SkinRequestException(String message) {
        super(message);
    }

    public SkinRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}