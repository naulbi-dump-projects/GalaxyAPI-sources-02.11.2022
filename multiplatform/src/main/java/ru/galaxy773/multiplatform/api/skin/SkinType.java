package ru.galaxy773.multiplatform.api.skin;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SkinType {

    ELY(0),
    MOJANG(1),
    CUSTOM(2);

    private static final TIntObjectMap<SkinType> SKIN_TYPES = new TIntObjectHashMap<>();

    private final int typeId;

    public static SkinType getSkinType(int type) {
        SkinType skinType = SKIN_TYPES.get(type);
        if (skinType != null) {
            return skinType;
        }
        return CUSTOM;
    }

    static {
        for (SkinType skinType : values()) {
            SKIN_TYPES.put(skinType.typeId, skinType);
        }
    }
}
