package ru.galaxy773.multiplatform.impl.skin.services;

import ru.galaxy773.multiplatform.impl.skin.exeptions.SkinRequestException;
import ru.galaxy773.multiplatform.impl.skin.exeptions.TooManyRequestsSkinException;
import ru.galaxy773.multiplatform.impl.skin.response.SkinProperty;
import ru.galaxy773.multiplatform.impl.skin.response.SkinResponse;
import ru.galaxy773.multiplatform.impl.skin.Skin;
import ru.galaxy773.multiplatform.api.skin.SkinType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public final class ElySkinService extends SkinService {

    private final String serverKey = ""; //todo мб он нужен

    public ElySkinService(String uuidUrl, String skinUrl) {
        super(uuidUrl, skinUrl);
    }

    @Override
    protected HttpURLConnection makeConnection(String url) throws IOException, SkinRequestException {
        HttpURLConnection connection = super.makeConnection(url);
        switch (connection.getResponseCode()) {
            case 204:
                throw new SkinRequestException("Скин не загружен... кажется что-то пошло не так");
            case 429:
                throw new TooManyRequestsSkinException();
            default:
                return connection;
        }
    }

    @Override
    public SkinType getSkinType() {
        return SkinType.ELY;
    }

    @Override
    public Skin getSkinByName(String name) throws SkinRequestException {
        try {
            SkinResponse skinResponse = readResponse(skinUrl + URLEncoder.encode(name, "UTF-8")
                    + "?unsigned=false&token="
                    + URLEncoder.encode(serverKey, "UTF-8"), SkinResponse.class);
            SkinProperty property = skinResponse.getProperties();
            return property.toSkin(name, getSkinType());
        } catch (IOException ignored) {
            throw new SkinRequestException("Произошла ошибка при загрузке скина");
        }
    }
}
