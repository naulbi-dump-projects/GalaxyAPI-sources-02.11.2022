package ru.galaxy773.bukkit.api.utils.version;

import com.viaversion.viaversion.api.Via;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;

@UtilityClass
public class ViaVersionUtil {

    public Version getVersion(Player player) {
        return Version.getVersion(Via.getAPI().getPlayerVersion(player.getUniqueId()));
    }
}
