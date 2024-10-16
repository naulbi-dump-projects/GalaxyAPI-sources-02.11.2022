package ru.galaxy773.bukkit.api.gamer;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.multiplatform.api.gamer.GamerAPI;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderAPI;
import ru.galaxy773.multiplatform.api.utils.StringUtil;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;

import java.util.Map;

@UtilityClass
public class GamerManager {

    public BukkitGamer getGamer(String name) {
        return (BukkitGamer) GamerAPI.getGamer(name);
    }

    public BukkitGamer getGamer(Player player) {
        if (player == null)
            return null;
        
        return getGamer(player.getName());
    }

    public BukkitGamer getGamer(int playerID) {
        return (BukkitGamer) GamerAPI.getGamer(playerID);
    }

    public void removeGamer(String name) {
        GamerAPI.removeGamer(name);
    }

    public void removeGamer(Player player) {
        removeGamer(player.getName());
    }

    public void removeGamer(BukkitGamer gamer) {
        removeGamer(gamer.getName());
    }

    public Map<String, BaseGamer> getGamers() {
        return GamerAPI.getGamers();
    }

    public IBaseGamer getFromCacheOrLoad(int playerID) {
        return GamerAPI.getFromCacheOrLoad(playerID);
    }

    public IBaseGamer getOrCreate(String name) {
        return GamerAPI.getFromCacheOrLoad(name);
    }

    static {
        PlaceholderAPI placeholderAPI = BukkitAPI.getPlaceholderAPI();
        placeholderAPI.registerGamerPlaceholder("%chatprefix%", (gamer) -> ((BukkitGamer) gamer).getTagPrefix());
        placeholderAPI.registerGamerPlaceholder("%tabprefix%", (gamer) -> ((BukkitGamer) gamer).getTagPrefix());
        placeholderAPI.registerGamerPlaceholder("%player%", IBaseGamer::getName);
        placeholderAPI.registerGamerPlaceholder("%coins%", gamer -> StringUtil.getNumberFormat(gamer.getCoins()));
        placeholderAPI.registerGamerPlaceholder("%gold%", gamer -> StringUtil.getNumberFormat(gamer.getGold()));
        placeholderAPI.registerGamerPlaceholder("%level%", gamer -> String.valueOf(gamer.getLevel()));
    }
}
