package ru.galaxy773.multiplatform.impl.skin.exeptions;

public class TooManyRequestsSkinException extends SkinRequestException {

    public TooManyRequestsSkinException() {
        super("Превышен лимит запросов");
    }
}
