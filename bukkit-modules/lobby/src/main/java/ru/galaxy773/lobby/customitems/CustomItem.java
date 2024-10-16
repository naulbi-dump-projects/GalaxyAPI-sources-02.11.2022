package ru.galaxy773.lobby.customitems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.usableitem.ClickAction;
import ru.galaxy773.bukkit.api.usableitem.ClickType;
import ru.galaxy773.bukkit.api.usableitem.UsableAPI;
import ru.galaxy773.bukkit.api.usableitem.UsableItem;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class CustomItem {

    private static final UsableAPI USABLE_API = BukkitAPI.getUsableAPI();
    private final UsableItem usableItem;

    public CustomItem(Material material, String key, ClickAction clickAction) {
        this(new ItemStack(material), key, clickAction);
    }

    public CustomItem(ItemStack itemStack, String key, ClickAction clickAction) {
        this.usableItem = USABLE_API.createUsableItem(ItemBuilder.builder(itemStack.clone())
                .setName(Lang.getMessage(key + "_NAME"))
                .setLore(Lang.getList(key + "_LORE"))
                .build(), clickAction);
        this.usableItem.setInvClick(true);
    }

    public void givePlayer(Player player, int slot) {
        player.getInventory().setItem(slot, this.usableItem.getItemStack());
    }

    public static void giveItems(Player player) {
        CustomItemType.MENU.getCustomItem().givePlayer(player, 0);
        CustomItemType.ACHIEVEMENTS.getCustomItem().givePlayer(player, 2);
        CustomItemType.DONATE.getCustomItem().givePlayer(player, 4);
        CustomItemType.LOBBY_SELECTOR.getCustomItem().givePlayer(player, 8);
    }

    public static void giveProfileItem(Player player) {
        BukkitGamer gamer = GamerManager.getGamer(player);
        UsableItem usableItem = USABLE_API.createUsableItem(ItemBuilder.builder(gamer.getHead().clone())
                .setName(Lang.getMessage("PROFILE_ITEM_NAME", gamer.getGroup().getChatPrefix() + gamer.getName()))
                .setLore(Lang.getList("PROFILE_ITEM_LORE")).build(),
                player, (clicker, clickType, block) -> {
            if (clickType != ClickType.LEFT) {
                clicker.chat("/profile");
            }
        });
        usableItem.setDropOnDeath(false);
        usableItem.setInvClick(true);
        player.getInventory().setItem(1, usableItem.getItemStack());
    }
}

