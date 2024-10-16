package ru.galaxy773.multiplatform.impl.skin.response;

import lombok.Getter;
import ru.galaxy773.multiplatform.impl.skin.exeptions.SkinRequestException;

public class SkinResponse extends SkinsResponse {

    @Getter
    private final SkinProperty properties;

    public SkinResponse(String id, String name, String message, String type, String error, int status, SkinProperty properties) {
        super(id, name, message, type, error, status);
        this.properties = properties;
    }

    @Override
    public void check() throws SkinRequestException {
        super.check();
        assert properties != null : "properties";
    }
}
