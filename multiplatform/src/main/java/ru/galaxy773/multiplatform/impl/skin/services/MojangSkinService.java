package ru.galaxy773.multiplatform.impl.skin.services;

import ru.galaxy773.multiplatform.impl.skin.exeptions.SkinRequestException;
import ru.galaxy773.multiplatform.impl.skin.response.SkinProperty;
import ru.galaxy773.multiplatform.impl.skin.response.SkinResponse;
import ru.galaxy773.multiplatform.impl.skin.response.UUIDResponse;
import ru.galaxy773.multiplatform.impl.skin.Skin;
import ru.galaxy773.multiplatform.api.skin.SkinType;

import java.io.IOException;
import java.net.URLEncoder;

public final class MojangSkinService extends SkinService {

    public MojangSkinService(String uuidUrl, String skinUrl) {
        super(uuidUrl, skinUrl);
    }

    @Override
    public SkinType getSkinType() {
        return SkinType.MOJANG;
    }

    @Override
    public Skin getSkinByName(String name) throws SkinRequestException {
        try {
            return getSkinByUUID(getUUID(name), name);
        } catch (Exception e) {
            return null;
        }
    }

    private String getUUID(String name) throws SkinRequestException {
        UUIDResponse response;
        try {
            response = readResponse(uuidUrl + URLEncoder.encode(name, "UTF-8"), UUIDResponse.class);
            if (!response.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("name");
            }
        } catch (SkinRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new SkinRequestException(name + " не имеет лицензии", e);
        }

        return response.getId();
    }

    public Skin getSkinByUUID(String uuid, String name) throws SkinRequestException {
        try {
            SkinResponse skinResponse = readResponse(skinUrl + URLEncoder.encode(uuid, "UTF-8")
                    + "?unsigned=false", SkinResponse.class);
            SkinProperty property = skinResponse.getProperties();
            return property.toSkin(name, getSkinType());
        } catch (IOException e) {
            throw new SkinRequestException("Произошла ошибка при загрузке скина");
        }
    }
}
