package ru.galaxy773.bukkit.guis.profile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.guis.profile.donatemenu.DonateMenuGui;
import ru.galaxy773.bukkit.guis.profile.tituls.TitulsCategoryGui;
import ru.galaxy773.bukkit.impl.gui.types.UpdatableGui;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;
import ru.galaxy773.multiplatform.api.utils.time.TimeUtil;
import ru.galaxy773.multiplatform.impl.skin.Skin;

public class ProfileMainPage extends UpdatableGui<GInventory> {

    private static final boolean USE_COSMETICS = Bukkit.getPluginManager().getPlugin("Cosmetics") != null;
    private static final boolean USE_ACHIEVEMENTS = Bukkit.getPluginManager().getPlugin("Achievements") != null;

    public ProfileMainPage(Player player) {
        super(player);
        createInventory();
        setActions();
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createInventory(this.player,
                Lang.getMessage("PROFILE_MAIN_GUI_NAME", this.player.getName()),
                5);
    }

    @Override
    protected void setDynamicItems() {
        inventory.setItem(new GItem(ItemBuilder.builder(this.gamer.getHead())
                .setName(Lang.getMessage("PROFILE_GUI_ITEM_NAME"))
                .setLore(Lang.getList("PROFILE_GUI_ITEM_LORE",
                        this.gamer.getGroup().getChatPrefix(),
                        this.gamer.getCoins(),
                        this.gamer.getLevel() + " \u00A7f\u0443\u0440. " + StringUtil.onPercentBar(this.gamer.getExp(), this.gamer.getTotalExpNextLevel()) + " \u00A76" + StringUtil.onPercent(this.gamer.getExp(), this.gamer.getTotalExpNextLevel()) + "%",
                        this.gamer.getFriendsIDs().size(),
                        this.gamer.getFriendsLimit(),
                        this.gamer.getSkin().getSkinName().equals(Skin.DEFAULT_SKIN.getSkinName()) ? "стандартный" : this.gamer.getSkin().getSkinName(),
                        this.gamer.getSelectedTitul().getTitul().getTitul(),
                        TimeUtil.getTimeLeft(this.gamer.getPlayTime()),
                        TimeUtil.getTimeLeft(this.gamer.getPlayTimeOnJoin())))
                .build()), 5, 2);
    }

    @Override
    protected void setStaticItems() {
        inventory.setItem(new GItem(ItemBuilder.builder(Material.EXP_BOTTLE)
                .setName(Lang.getMessage("LEVEL_GUI_ITEM_NAME"))
                .setLore(Lang.getList("LEVEL_GUI_ITEM_LORE",
                        this.gamer.getLevel(),
                        StringUtil.onPercentBar(this.gamer.getExp(), this.gamer.getTotalExpNextLevel()),
                        StringUtil.onPercent(this.gamer.getExp(), this.gamer.getTotalExpNextLevel()) + "%",
                        this.gamer.getTotalExpNextLevel() - this.gamer.getExp()))
                .build(),
                ((clicker, clickType, slot) ->
                        GUI_MANAGER.getGui(ru.galaxy773.bukkit.guis.profile.RewardGui.class, this.player).open())), 5, 3);
        String enable = "\u00A7a\u0432\u043A\u043B\u044E\u0447\u0435\u043D\u043E";
        String disable = "\u00A7c\u0432\u044B\u043A\u043B\u044E\u0447\u0435\u043D\u043E";
        inventory.setItem(new GItem(ItemBuilder.builder(Material.REDSTONE_COMPARATOR)
                .setName(Lang.getMessage("SETTINGS_GUI_ITEM_NAME"))
                .setLore(Lang.getList("SETTINGS_GUI_ITEM_LORE",
                        this.gamer.getSetting(SettingsType.HIDER) ? enable : disable,
                        this.gamer.getSetting(SettingsType.BLOOD) ? enable : disable,
                        this.gamer.getSetting(SettingsType.FLY) ? enable : disable,
                        this.gamer.getSetting(SettingsType.CHAT) ? enable : disable,
                        this.gamer.getSetting(SettingsType.SCOREBOARD) ? enable : disable,
                        this.gamer.getSetting(SettingsType.PRIVATE_MESSAGE) ? enable : disable,
                        this.gamer.getSetting(SettingsType.FRIENDS_REQUEST) ? enable : disable,
                        this.gamer.getSetting(SettingsType.CLANS_REQUEST) ? enable : disable,
                        this.gamer.getSetting(SettingsType.DONATE_CHAT) ? enable : disable,
                        this.gamer.getSetting(SettingsType.BOSSBAR) ? enable : disable,
                        this.gamer.getSetting(SettingsType.AUTOMESSAGES) ? enable : disable))
                .build(),
                ((clicker, clickType, slot) -> GUI_MANAGER.getGui(SettingsGui.class, this.player).open())), 4, 3);
        inventory.setItem(new GItem(ItemBuilder.builder(Material.EMERALD)
                .setName(Lang.getMessage("DONATE_GUI_ITEM_NAME"))
                .setLore(Lang.getList("DONATE_GUI_ITEM_LORE"))
                .build(),
                ((clicker, clickType, slot) -> clicker.chat("/donate"))), 7, 2);
        inventory.setItem(new GItem(ItemBuilder.builder(Material.BOOK_AND_QUILL)
                .setName(Lang.getMessage("INFORMATION_GUI_ITEM_NAME"))
                .setLore(Lang.getList("INFORMATION_GUI_ITEM_LORE"))
                .build(),
                ((clicker, clickType, slot) -> GUI_MANAGER.getGui(InformationGui.class, this.player).open())), 5, 4);
        inventory.setItem(new GItem(ItemBuilder.builder(Material.NAME_TAG)
                .setName(Lang.getMessage("TITULS_GUI_ITEM_NAME"))
                .setLore(Lang.getList("TITULS_GUI_ITEM_LORE", this.gamer.getTituls().size(), TitulType.values().length - 1, StringUtil.onPercent(this.gamer.getTituls().size(), TitulType.values().length - 1) + "%"))
                .build(),
                ((clicker, clickType, slot) -> GUI_MANAGER.getGui(TitulsCategoryGui.class, this.player).open())), 7, 4);
        inventory.setItem(new GItem(ItemBuilder.builder(Material.ENCHANTMENT_TABLE)
                .setName(Lang.getMessage("DONATE_MENU_GUI_ITEM_NAME"))
                .setLore(Lang.getList("DONATE_MENU_GUI_ITEM_LORE"))
                .build(),
                ((clicker, clickType, slot) -> GUI_MANAGER.getGui(DonateMenuGui.class, this.player).open())), 3, 2);
        inventory.setItem(new GItem(ItemBuilder.builder(Material.SPECTRAL_ARROW)
                .setName(Lang.getMessage("EFFECTS_GUI_ITEM_NAME"))
                .setLore(Lang.getList("EFFECTS_GUI_ITEM_LORE"))
                .build(),
                ((clicker, clickType, slot) -> {
                    if (!USE_COSMETICS) {
                        clicker.sendMessage(Lang.getMessage("COSMETICS_NOT_LOADED"));
                        return;
                    }
                    clicker.chat("/effects");
                })), 3, 4);
        inventory.setItem(new GItem(ItemBuilder.builder(Material.PAPER)
                .setName(Lang.getMessage("ACHIEVEMENTS_GUI_ITEM_NAME"))
                .setLore(Lang.getList("ACHIEVEMENTS_GUI_ITEM_LORE"))
                .build(),
                ((clicker, clickType, slot) -> {
                    if (!USE_ACHIEVEMENTS) {
                        clicker.sendMessage(Lang.getMessage("ACHIEVEMENTS_NOT_LOADED"));
                        return;
                    }
                    clicker.chat("/achievements");
                })), 6, 3);
    }
}
