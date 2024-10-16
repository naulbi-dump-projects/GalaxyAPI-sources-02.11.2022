package ru.galaxy773.lobby.customitems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import ru.galaxy773.bukkit.api.usableitem.ClickType;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;

@AllArgsConstructor
@Getter
public enum CustomItemType {
    MENU(new CustomItem(Material.COMPASS, "SELECTOR_MENU_ITEM", (player, clickType, block) -> {
        if (clickType != ClickType.LEFT) {
            player.chat("/menu");
        }
    })),
    ACHIEVEMENTS(new CustomItem(Material.PAPER, "ACHIEVEMENTS_MENU_ITEM", (player, clickType, block) -> {
        if (clickType != ClickType.LEFT) {
            player.chat("/achievements");
        }
    })),
    DONATE(new CustomItem(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY0YTkxNDMzZDY2YmZjNmVmMWM5YWM0ZTM3MWE4ODZhMWM0NjJjOTc1ZmY4MTI3NzYxZDcwM2UwYTNlNzMwNiJ9fX0="), "DONATE_MENU_ITEM", (player, clickType, block) -> {
        if (clickType != ClickType.LEFT) {
            player.chat("/donate");
        }
    })),
    LOBBY_SELECTOR(new CustomItem(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThmZWQxMjI4NWYxZDliZjI0NWZlZGU0MTQ3YjhjZGE5M2Q1OTM4M2NlOGE1OGQ2Mjk5ZGQ0ZWFlOTg0MyJ9fX0="), "LOBBY_SELECTOR_ITEM", (player, clickType, block) -> {
        if (clickType != ClickType.LEFT) {
            player.chat("/selector");
        }
    }));

    private final CustomItem customItem;
}

