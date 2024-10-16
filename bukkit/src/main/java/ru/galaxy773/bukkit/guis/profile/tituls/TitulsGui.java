package ru.galaxy773.bukkit.guis.profile.tituls;

import lombok.Setter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.Titul;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulsCategory;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;

import java.util.Arrays;

public class TitulsGui extends AbstractGui<MultiInventory> {
    
    @Setter
    private TitulsCategory category;
    
    public TitulsGui(Player player) {
        super(player);
        setRecreateOnOpen(true);
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createMultiInventory(
                player,
                Lang.getMessage("TITULS_CATEGORY_GUI_NAME"),
                5);
    }

    @Override
    protected void setStaticItems() {
        int slot = 10;
        int page = 0;
        for (TitulType titulType : Arrays.stream(TitulType.values())
                .skip(1)
                .parallel()
                .filter(t -> t.getCategory() == category)
                .toArray(TitulType[]::new)) {
            
            if (slot == 17) {
                slot = 19;
            } else if (slot == 26) {
                slot = 28;
            } else if (slot == 35) {
                slot = 10;
                page++;
            }

            Titul titul = titulType.getTitul();
            if (gamer.getTituls().contains(titulType)) {
                if (gamer.getSelectedTitul() == titulType) {
                    inventory.setItem(page, slot, new GItem(ItemBuilder.builder(HeadUtil.getYellowHead())
                            .setName(Lang.getMessage("TITUL_SELECTED_GUI_ITEM_NAME", titul.getTitul()))
                            .setLore(Lang.getList("TITUL_SELECTED_GUI_ITEM_LORE", titul.getRarity().getName() + " " + titul.getRarity().getStars()))
                            .build(),
                            ((clicker, clickType, i) ->
                                    gamer.sendMessageLocale("TITUL_IS_SELECTED", titul.getTitul()))));
                } else {
                    inventory.setItem(page, slot, new GItem(ItemBuilder.builder(HeadUtil.getAcceptHead())
                            .setName(Lang.getMessage("TITUL_ACCESS_GUI_ITEM_NAME", titul.getTitul()))
                            .setLore(Lang.getList("TITUL_ACCESS_GUI_ITEM_LORE", titul.getRarity().getName() + " " + titul.getRarity().getStars()))
                            .build(),
                            ((clicker, clickType, i) -> {
                                if (Cooldown.hasCooldown(gamer.getName(), "TITUL_SELECT")) {
                                    gamer.sendMessageLocale("TITUL_SELECT_COOLDOWN", Cooldown.getCooldownLeft(gamer.getName(), "TITUL_SELECT"));
                                    return;
                                }
                                gamer.setSelectedTitul(titulType);
                                BukkitUtil.callEvent(new GamerChangeCustomizationEvent(gamer, CustomizationType.TITUL));
                                gamer.sendMessageLocale("TITUL_SELECT", titul.getTitul());
                                setStaticItems();
                                Cooldown.addCooldown(gamer.getName(), "TITUL_SELECT", 60);
                            })));
                }
            } else {
                inventory.setItem(page, slot, new GItem(ItemBuilder.builder(HeadUtil.getDenyHead())
                        .setName(Lang.getMessage("TITUL_DENY_GUI_ITEM_NAME", titul.getTitul()))
                        .setLore(Lang.getList("TITUL_DENY_GUI_ITEM_LORE", titul.getRarity().getName() + " " + titul.getRarity().getStars()))
                        .replaceLore("{get_way}", titul.getLore())
                        .build(),
                        ((clicker, clickType, slot1) -> {
                            if (titul.buy((BaseGamer) gamer)) {
                                gamer.addTitul(titulType);
                                setStaticItems();
                                gamer.sendMessageLocale("TITUL_BUY", titul.getTitul());
                            } else {
                                gamer.sendMessage(titul.getBuyErrorMessage((BaseGamer) gamer));
                            }
                        })));
            }

            slot++;
        }
        
        INVENTORY_API.backButton(this.inventory, ((clicker, clickType, slot1) -> clicker.chat("/tituls")), 40);
        INVENTORY_API.pageButton(this.inventory, 38, 42);
    }
}
