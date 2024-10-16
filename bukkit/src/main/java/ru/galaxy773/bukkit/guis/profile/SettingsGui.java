package ru.galaxy773.bukkit.guis.profile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.game.SubType;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

import java.util.EnumMap;
import java.util.Map;

public class SettingsGui extends AbstractGui<MultiInventory> {

    private static final Map<SettingsType, ItemStack> ICONS = new EnumMap<>(SettingsType.class);

    public SettingsGui(Player player) {
        super(player);
        createInventory();
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createMultiInventory(
                this.player,
                Lang.getMessage("SETTINGS_GUI_NAME"),
                5);

    }

    @Override
    protected void setStaticItems() {
        int slot = 10;
        int page = 0;

        ItemStack enabledStack = ItemBuilder.builder(new ItemStack(Material.INK_SACK, 1, (short) 10))
                .setName(Lang.getMessage("SETTINGS_GUI_BUTTON_ENABLED_NAME"))
                .setLore(Lang.getList("SETTINGS_GUI_BUTTON_ENABLED_LORE"))
                .build();
        ItemStack disabledStack = ItemBuilder.builder(new ItemStack(Material.INK_SACK, 1, (short) 8))
                .setName(Lang.getMessage("SETTINGS_GUI_BUTTON_DISABLED_NAME"))
                .setLore(Lang.getList("SETTINGS_GUI_BUTTON_DISABLED_LORE"))
                .build();

        for (SettingsType setting : SettingsType.values()) {
            if (slot == 17) {
                slot = 10;
                page++;
            }

            ItemBuilder itemBuilder = ItemBuilder.builder(ICONS.get(setting).clone())
                    .setName(Lang.getMessage(setting.name() + "_GUI_ITEM_NAME"))
                    .setLore(Lang.getList(setting.name() + "_GUI_ITEM_LORE"));
            if (!gamer.hasChildGroup(setting.getGroup())) {
                itemBuilder.addLore("", Lang.getMessage("SETTING_ACCESS_STATUS", setting.getGroup().getChatPrefix()));
            }

            inventory.setItem(page, slot, new GItem(itemBuilder.build()));

            boolean enabled = gamer.getSetting(setting);
            inventory.setItem(page, slot + 9, new GItem(enabled ? enabledStack.clone() : disabledStack.clone(),
                    ((clicker, clickType, i) -> {
                        if (!gamer.hasChildGroup(setting.getGroup())) {
                            gamer.sendMessageLocale("SETTING_NOT_ACCESS", setting.getGroup().getChatPrefix());
                            return;
                        }
                        if (!SubType.isHub() && !SubType.isGameLobby() &&
                                (setting == SettingsType.HIDER || setting == SettingsType.SCOREBOARD
                                || setting == SettingsType.BOSSBAR || setting == SettingsType.FLY)) {
                            gamer.sendMessageLocale("SETTING_DISABLED_ON_THIS_GAME");
                            return;
                        }
                        if (Cooldown.hasCooldown(gamer.getName(), "SETTINGS_CHANGE")) {
                            gamer.sendMessageLocale("SETTING_CHANGE_COOLDOWN", Cooldown.getCooldownLeft(gamer.getName(), "SETTINGS_CHANGE"));
                            return;
                        }
                        GamerChangeSettingEvent event = new GamerChangeSettingEvent(this.gamer, setting, !enabled);
                        BukkitUtil.callEvent(event);
                        if (event.isCancelled()) {
                            return;
                        }
                        gamer.setSetting(setting, !enabled, true);
                        Cooldown.addCooldown(gamer.getName(), "SETTINGS_CHANGE", 60);
                        setStaticItems();
                    })));
            slot++;
        }

        INVENTORY_API.backButton(this.inventory, (clicker, clickType, i) -> clicker.chat("/profile"), 40);
        INVENTORY_API.pageButton(inventory, 38, 42);
    }

    static {
        ICONS.put(SettingsType.HIDER, new ItemStack(Material.getMaterial(347)));
        ICONS.put(SettingsType.BLOOD, new ItemStack(Material.INK_SACK, 1, (short) 1));
        ICONS.put(SettingsType.FLY, new ItemStack(Material.ELYTRA));
        ICONS.put(SettingsType.CHAT, new ItemStack(Material.PAPER));
        ICONS.put(SettingsType.SCOREBOARD, new ItemStack(Material.SIGN));
        ICONS.put(SettingsType.PRIVATE_MESSAGE, new ItemStack(Material.KNOWLEDGE_BOOK));
        ICONS.put(SettingsType.FRIENDS_REQUEST, HeadUtil.getEmptyPlayerHead());
        ICONS.put(SettingsType.CLANS_REQUEST, new ItemStack(Material.DIAMOND_CHESTPLATE));
        ICONS.put(SettingsType.DONATE_CHAT, new ItemStack(Material.ENCHANTED_BOOK));
        ICONS.put(SettingsType.BOSSBAR, new ItemStack(Material.DRAGON_EGG));
        ICONS.put(SettingsType.AUTOMESSAGES, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmUzZGIyN2NiZDE3ODkzMTA0MDkwODFhZDhjNDJkNjkwYjA4OTYxYjU1Y2FkZDQ1YjQyZDQ2YmNhMjhiOCJ9fX0="));
    }
}
