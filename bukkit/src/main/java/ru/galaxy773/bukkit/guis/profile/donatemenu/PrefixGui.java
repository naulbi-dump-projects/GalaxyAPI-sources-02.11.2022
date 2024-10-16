package ru.galaxy773.bukkit.guis.profile.donatemenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.customization.PrefixColor;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

import java.util.EnumMap;
import java.util.Map;

public class PrefixGui extends DonateMenuGui {
    
    private static final int[] PREFIX_SLOTS = new int[] {
            11, 12, 13, 14, 15,
            20, 21, 22, 23, 24
    };
    private static final Map<PrefixColor, ItemStack> ICONS = new EnumMap<>(PrefixColor.class);

    public PrefixGui(Player player) {
        super(player);
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(
                this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(DonateMenuGui.class, this.player).open(),
                40);
        int index = 0;
        boolean chronos = gamer.getGroup() == Group.CHRONOS;
        for (PrefixColor prefixColor : PrefixColor.values()) {
            if (chronos) {
                if (gamer.getPrefixColor() == prefixColor) {
                    inventory.setItem(PREFIX_SLOTS[index], new GItem(ItemBuilder.builder(ICONS.get(prefixColor).clone())
                            .setName(Lang.getMessage("PREFIX_SELECTED_GUI_ITEM_NAME", prefixColor.getColorCode() + prefixColor.getName()))
                            .setLore(Lang.getList("PREFIX_SELECTED_GUI_ITEM_LORE", prefixColor.getColorCode()))
                            .build()));
                } else {
                    inventory.setItem(PREFIX_SLOTS[index], new GItem(ItemBuilder.builder(ICONS.get(prefixColor).clone())
                            .setName(Lang.getMessage("PREFIX_ACCESS_GUI_ITEM_NAME", prefixColor.getColorCode() + prefixColor.getName()))
                            .setLore(Lang.getList("PREFIX_ACCESS_GUI_ITEM_LORE", prefixColor.getColorCode()))
                            .build(),
                            ((clicker, clickType, slot) -> {
                                if (Cooldown.hasCooldown(gamer.getName(), "PREFIX_SELECT")) {
                                    gamer.sendMessageLocale("PREFIX_SELECT_COOLDOWN", Cooldown.getCooldownLeft(gamer.getName(), "PREFIX_SELECT"));
                                    return;
                                }
                                gamer.setPrefixColor(prefixColor);
                                BukkitUtil.callEvent(new GamerChangeCustomizationEvent(gamer, CustomizationType.PREFIX_COLOR));
                                gamer.sendMessageLocale("PREFIX_SELECT", prefixColor.getColorCode());
                                gamer.getPlayer().closeInventory();
                                Cooldown.addCooldown(gamer.getName(), "PREFIX_SELECT", 60);
                            })));
                }
            } else {
                inventory.setItem(PREFIX_SLOTS[index], new GItem(ItemBuilder.builder(ICONS.get(prefixColor).clone())
                        .setName(Lang.getMessage("PREFIX_DENY_GUI_ITEM_NAME", prefixColor.getColorCode() + prefixColor.getName()))
                        .setLore(Lang.getList("PREFIX_DENY_GUI_ITEM_LORE", prefixColor.getColorCode(), Group.CHRONOS.getChatPrefix()))
                        .build()));
            }
            index++;
        }
    }

    static {
        ICONS.put(PrefixColor.CHRONOS_1, new ItemStack(Material.BANNER, 1, (short) 1));
        ICONS.put(PrefixColor.CHRONOS_2, new ItemStack(Material.BANNER, 1, (short) 11));
        ICONS.put(PrefixColor.CHRONOS_3, new ItemStack(Material.BANNER, 1, (short) 12));
        ICONS.put(PrefixColor.CHRONOS_4, new ItemStack(Material.BANNER, 1, (short) 10));
        ICONS.put(PrefixColor.CHRONOS_5, new ItemStack(Material.BANNER, 1, (short) 4));
        ICONS.put(PrefixColor.CHRONOS_6, new ItemStack(Material.BANNER, 1, (short) 2));
        ICONS.put(PrefixColor.CHRONOS_7, new ItemStack(Material.BANNER, 1, (short) 14));
        ICONS.put(PrefixColor.CHRONOS_8, new ItemStack(Material.BANNER, 1, (short) 6));
        ICONS.put(PrefixColor.CHRONOS_9, new ItemStack(Material.BANNER, 1, (short) 13));
        ICONS.put(PrefixColor.CHRONOS_10, new ItemStack(Material.BANNER, 1, (short) 5));

    }
}
