package ru.galaxy773.bukkit.api.utils.skin;

import lombok.experimental.UtilityClass;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSkinEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerSkinApplyEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.multiplatform.api.skin.SkinType;
import ru.galaxy773.multiplatform.api.sound.SoundType;
import ru.galaxy773.multiplatform.impl.gamer.sections.SkinSection;
import ru.galaxy773.multiplatform.impl.skin.Skin;

@UtilityClass
public class SkinUtil {

    private static final NmsManager NMS_MANAGER = NmsAPI.getManager();

    public static void setSkin(BukkitGamer gamer, String skinName, String value, String signature, SkinType skinType) {
        if (gamer == null || !gamer.isOnline())
            return;

        SkinSection section = gamer.getSection(SkinSection.class);
        if (section == null)
            return;

        Skin skin = new Skin(skinName, value, signature, skinType);

        gamer.playSound(SoundType.DESTROY);

        GamerSkinApplyEvent skinApplyEvent = new GamerSkinApplyEvent(gamer);
        BukkitUtil.callEvent(skinApplyEvent);
        if (skinApplyEvent.isCancelled())
            return;

        section.updateSkin(skin, false);
        gamer.setHead(HeadUtil.getHead(value));

        NMS_MANAGER.setSkin(gamer.getPlayer(), value, signature);

        BukkitUtil.callEvent(new GamerChangeSkinEvent(gamer, skin));
    }
}

