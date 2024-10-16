package ru.galaxy773.multiplatform.impl.skin;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.galaxy773.multiplatform.impl.skin.exeptions.SkinRequestException;
import ru.galaxy773.multiplatform.impl.skin.services.ElySkinService;
import ru.galaxy773.multiplatform.impl.skin.services.MojangSkinService;
import ru.galaxy773.multiplatform.impl.skin.services.SkinService;

@UtilityClass
@Getter
public class SkinAPI {

    private final SkinService ELY_API = new ElySkinService("https://account.ely.by/api/mojang/profiles/",
                                               "http://skinsystem.ely.by/textures/signed/");
    private final SkinService MOJANG_API = new MojangSkinService("https://api.mojang.com/users/profiles/minecraft/",
                                                   "https://sessionserver.mojang.com/session/minecraft/profile/");

    public Skin getSkin(String skinName) {
        if (skinName.isEmpty() || skinName.equalsIgnoreCase("FixMine_ru")) {
            return Skin.DEFAULT_SKIN;
        }
        try {
            return SkinAPI.MOJANG_API.getSkinByName(skinName);
        }
        catch (SkinRequestException e) {
            try {
                return SkinAPI.ELY_API.getSkinByName(skinName);
            }
            catch (SkinRequestException e2) {
                return null;
            }
        }
    }
}
