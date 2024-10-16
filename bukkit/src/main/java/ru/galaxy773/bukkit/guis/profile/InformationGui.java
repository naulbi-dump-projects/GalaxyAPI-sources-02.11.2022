package ru.galaxy773.bukkit.guis.profile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemUtil;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class InformationGui extends AbstractGui<GInventory> {

    private final CoreAPI coreAPI = BukkitAPI.getCoreAPI();

    public InformationGui(Player player) {
        super(player);
        this.createInventory();
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createInventory(Lang.getMessage("INFORMATION_GUI_NAME"),  5);
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(this.inventory, (clicker, clickType, slot) -> clicker.chat("/profile"), 40);
        this.inventory.setItem(11, new GItem(ItemBuilder.builder(Material.COMPASS)
                .setName(Lang.getMessage("INFORMATION_NAVIGATION_NAME"))
                .setLore(Lang.getList("INFORMATION_NAVIGATION_LORE"))
                .build()));
        this.inventory.setItem(12, new GItem(ItemBuilder.builder(Material.EMERALD)
                .setName(Lang.getMessage("INFORMATION_DONATE_NAME"))
                .setLore(Lang.getList("INFORMATION_DONATE_LORE"))
                .build(),
                ((clicker, clickType, slot) -> {
                    clicker.closeInventory();
                    clicker.chat("/donate");
                })));
        this.inventory.setItem(13, new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNkMTg3N2JlOTVhOWVkYjg2ZGYyMjU2ZjIzOTU4MzI0YzJlYzE5ZWY5NDI3N2NlMmZiNWMzMzAxODQxZGMifX19"))
                .setName(Lang.getMessage("INFORMATION_FRIENDS_NAME"))
                .setLore(Lang.getList("INFORMATION_FRIENDS_LORE"))
                .build(),
                ((clicker, clickType, slot) -> {
                    clicker.closeInventory();
                    coreAPI.executeCommand(clicker, "friends");
                })));
        this.inventory.setItem(14, new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE0NDk4NmViZmI4NzU3ZGExMzc1Zjc0OTYxMDM0NTRlNmZjMWM1ZWY3M2QwMTNmYWNlNzQ5OWIxMzE4NGY3In19fQ=="))
                .setName(Lang.getMessage("INFORMATION_SECURITY_NAME"))
                .setLore(Lang.getList("INFORMATION_SECURITY_LORE"))
                .build()));
        this.inventory.setItem(15, new GItem(ItemBuilder.builder(Material.ARMOR_STAND)
                .setName(Lang.getMessage("INFORMATION_SKIN_NAME"))
                .setLore(Lang.getList("INFORMATION_SKIN_LORE"))
                .build(),
                ((clicker, clickType, slot) -> {
                    clicker.closeInventory();
                    clicker.chat("/skin");
                })));
        this.inventory.setItem(21, new GItem(ItemBuilder.builder(Material.BOOK_AND_QUILL)
                .setName(Lang.getMessage("INFORMATION_COMMUNICATION_NAME"))
                .setLore(Lang.getList("INFORMATION_COMMUNICATION_LORE"))
                .build()));
        this.inventory.setItem(22, new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmJkNzNkNjE2ZDIxYzE5MzAxZjJmMDc2Y2JjNTQ3YzdjMWI1MWJkNWUxYTQ1ZDdjNTlkNWFkYjgyODA4ZSJ9fX0="))
                .setName(Lang.getMessage("INFORMATION_LINKS_NAME"))
                .setLore(Lang.getList("INFORMATION_LINKS_LORE"))
                .build()));
        this.inventory.setItem(23, new GItem(ItemBuilder.builder(ItemUtil.getMaterialByVersion("DOUBLE_PLANT", "LEGACY_DOUBLE_PLANT"))
                .setName(Lang.getMessage("INFORMATION_CURRENCY_NAME"))
                .setLore(Lang.getList("INFORMATION_CURRENCY_LORE"))
                .build()));
    }
}
