package ru.galaxy773.bukkit.nms.interfaces.skins;

import org.bukkit.entity.Player;
import ru.galaxy773.multiplatform.impl.skin.Skin;

public interface SkinFactory {

    void applySkin(Player player, Skin skin);

    void updateSkin(Player player);
}
