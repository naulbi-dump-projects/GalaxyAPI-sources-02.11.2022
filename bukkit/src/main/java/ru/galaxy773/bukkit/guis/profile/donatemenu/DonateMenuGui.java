package ru.galaxy773.bukkit.guis.profile.donatemenu;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.guis.profile.ProfileMainPage;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.customization.FastMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.JoinMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.QuitMessage;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class DonateMenuGui extends AbstractGui<GInventory> {

    protected static final int[] messagesSlots = new int[] {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            29, 30, 31, 32, 33
    };

    public DonateMenuGui(Player player) {
        super(player);
        createInventory();
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createInventory(
                this.player,
                Lang.getMessage("DONATE_MENU_GUI_NAME"),
                5);
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(
                this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(ProfileMainPage.class, this.player).open(),
                40);

        inventory.setItem(new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0="))
                .setName(Lang.getMessage("JOIN_MESSAGES_GUI_ITEM_NAME"))
                .setLore(Lang.getList("JOIN_MESSAGES_GUI_ITEM_LORE", JoinMessage.JOIN_MESSAGE_1.getGroup().getChatPrefix()))
                .build(),
                ((clicker, clickType, slot) -> {
                    GUI_MANAGER.getGui(JoinMessagesGui.class, gamer.getPlayer()).open();
                })), 2,  3);
        inventory.setItem(new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlYWJhZTc2Mjk0ZGFiNTM2NjI3ZGIyMTk3ZWE0YzkyNWFjNmJmN2VhMDNkOTVkMmYxNGUxNzI1NTFlY2I0YiJ9fX0="))
                .setName(Lang.getMessage("FAST_MESSAGES_GUI_ITEM_NAME"))
                .setLore(Lang.getList("FAST_MESSAGES_GUI_ITEM_LORE", FastMessage.FAST_MESSAGE_1.getGroup().getChatPrefix()))
                .build(),
                ((clicker, clickType, slot) -> {
                    GUI_MANAGER.getGui(FastMessagesGui.class, gamer.getPlayer()).open();
                })), 4, 3);
        inventory.setItem(new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE2MjYzOTRkOTY0NjJmNjJlMGIyOTk2ZmFjYjQwMGJkYjc4YmRiOTlmMGQxODk2OTJlZTk2NDdkMTk3NTgzOSJ9fX0="))
                .setName(Lang.getMessage("QUIT_MESSAGES_GUI_ITEM_NAME"))
                .setLore(Lang.getList("QUIT_MESSAGES_GUI_ITEM_LORE", QuitMessage.QUIT_MESSAGE_1.getGroup().getChatPrefix()))
                .build(),
                ((clicker, clickType, slot) -> {
                    GUI_MANAGER.getGui(QuitMessagesGui.class, gamer.getPlayer()).open();
                })), 6,  3);
        inventory.setItem(new GItem(ItemBuilder.builder(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiMjhlNjUyZTY2MGZiNjY4ZGQ4MDllYTBjOWU0NWYwZDZiYWU5ZTM0MGIyYjJlZjhiNDg5ODg1ZDg5In19fQ=="))
                .setName(Lang.getMessage("PREFIX_GUI_ITEM_NAME"))
                .setLore(Lang.getList("PREFIX_GUI_ITEM_LORE", Group.CHRONOS.getChatPrefix()))
                .build(),
                ((clicker, clickType, slot) -> {
                    GUI_MANAGER.getGui(PrefixGui.class, gamer.getPlayer()).open();
                })), 8,  3);
    }

    static {
        GUI_MANAGER.createGui(FastMessagesGui.class);
        GUI_MANAGER.createGui(JoinMessagesGui.class);
        GUI_MANAGER.createGui(QuitMessagesGui.class);
        GUI_MANAGER.createGui(PrefixGui.class);
    }
}
