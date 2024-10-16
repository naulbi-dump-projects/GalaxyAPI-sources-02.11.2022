package ru.galaxy773.multiplatform.impl.skin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.galaxy773.multiplatform.impl.skin.Skin;
import ru.galaxy773.multiplatform.api.skin.SkinType;

@AllArgsConstructor
@Getter
public class SkinProperty {

    private final String name;
    private final String value;
    private final String signature;

    public Skin toSkin(String skinName, SkinType skinType) {
        return new Skin(skinName, value, signature, skinType);
    }
}
