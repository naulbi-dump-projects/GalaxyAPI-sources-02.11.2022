package ru.galaxy773.bukkit.guis.profile.tituls;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.guis.profile.ProfileMainPage;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulsCategory;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class TitulsCategoryGui extends AbstractGui<GInventory> {

    private static final Map<TitulsCategory, ItemStack> ICONS = new EnumMap<>(TitulsCategory.class);

    public TitulsCategoryGui(Player player) {
        super(player);
        createInventory();
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createInventory(
                player,
                Lang.getMessage("TITULS_CATEGORY_GUI_NAME"),
                5);
    }
    //12

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(
                this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(ProfileMainPage.class, this.player).open(),
                39);
        int slot = 12;
        for (TitulsCategory titulCategory : TitulsCategory.values()) {
            if (slot == 15)
                slot = 21;

            int unlockedTituls = (int) gamer.getTituls()
                    .parallelStream()
                    .filter(titul -> titul.getCategory() == titulCategory)
                    .count();

            int totalTituls = (int) Arrays.stream(TitulType.values())
                    .parallel()
                    .skip(1)
                    .filter(titul -> titul.getCategory() == titulCategory)
                    .count();

            inventory.setItem(slot, new GItem(ItemBuilder.builder(ICONS.get(titulCategory).clone())
                    .setName(Lang.getMessage("TITULS_" + titulCategory.name() + "_CATEGORY_ITEM_NAME"))
                    .setLore(Lang.getList("TITULS_" + titulCategory.name() + "_CATEGORY_ITEM_LORE",
                            unlockedTituls, totalTituls, StringUtil.onPercent(unlockedTituls, totalTituls) + "%"))
                    .build(),
                    ((clicker, clickType, i) -> {
                        TitulsGui titulsGui = GUI_MANAGER.getGui(TitulsGui.class, clicker);
                        titulsGui.setCategory(titulCategory);
                        titulsGui.open();
                    })));

            slot++;
        }
        this.inventory.setItem(new GItem(ItemBuilder.builder(Material.BARRIER)
                .setName(Lang.getMessage("TITUL_CLEAR_NAME"))
                .setLore(Lang.getList("TITUL_CLEAR_LORE"))
                .build(),
                ((clicker, clickType, i) -> {
                    if (gamer.getSelectedTitul() == TitulType.NONE) {
                        gamer.sendMessageLocale("TITUL_NOT_SELECTED");
                        return;
                    }
                    gamer.setSelectedTitul(TitulType.NONE);
                    BukkitUtil.callEvent(new GamerChangeCustomizationEvent(gamer, CustomizationType.TITUL));
                    gamer.sendMessageLocale("TITUL_CLEAR");
                    setStaticItems();
                })), 6, 5);
    }

    static {
        GUI_MANAGER.createGui(TitulsGui.class);
        ICONS.put(TitulsCategory.BASE, new ItemStack(Material.KNOWLEDGE_BOOK));
        ICONS.put(TitulsCategory.SPECIAL, new ItemStack(Material.NETHER_STAR));
        ICONS.put(TitulsCategory.NEW_YEAR, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JiZThmZDFhYTM5ZjE1MDc2ZTg4NGRmZTZkZGI5YTNmMzc2MWRiMzFlMmIxZjk5NDBiNWRmZDM0ZDFjNGQifX19"));
        ICONS.put(TitulsCategory.ANIME, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM1MTRlY2EwMzVkOTc0MThlZGQ3NWVmMTBkNDZhYzQ2ZjQzY2Y0YmQ2ZmJlOTI1M2U3YTY2ZWRiOTIwMzk2In19fQ=="));
        ICONS.put(TitulsCategory.MARVEL, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjUyY2E5NzgzNGUwMTQ1ODY5MWM5MDU0NDU3ODZkMTI2MmQ4NDM2NjNkNjEyOGYzYmMwYzUzMTliOGE2OWU0In19fQ=="));
        ICONS.put(TitulsCategory.GAMING, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFmMTY4NDUwNmRkNDEwMGE3ODFkNjAwNjYyMzU5M2U1MTVjM2U0OTAwMjBhNWExODNkODE0YmQ4Y2I5NGQ2ZiJ9fX0="));
    }
}
