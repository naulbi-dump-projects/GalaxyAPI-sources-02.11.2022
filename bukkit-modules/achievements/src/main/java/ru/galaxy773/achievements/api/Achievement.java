package ru.galaxy773.achievements.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.locale.Lang;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class Achievement {

    private final String key;
    @Getter
    private final int progress;
    @Getter
    private final ItemStack icon;
    @Getter
    private final Consumer<BukkitGamer> complete;

    public String getName(Object... replace) {
        return Lang.getMessage(this.key + "_NAME", replace);
    }

    public List<String> getCompletedLore(Object... replace){
        return Lang.getList(this.key + "_COMPLETED_LORE", replace);
    }

    public List<String> getNotCompletedLore(Object... replace) {
        return Lang.getList(this.key + "_NOT_COMPLETED_LORE", replace);
    }

    public String getTitle(Object... replace) {
        return Lang.getMessage(this.key + "_TITLE", replace);
    }

    public String getSubtitle(Object... replace) {
        return Lang.getMessage(this.key + "_SUBTITLE", replace);
    }
}
