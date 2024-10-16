package ru.galaxy773.bukkit.guis.profile.donatemenu;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.multiplatform.api.gamer.customization.QuitMessage;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

public class QuitMessagesGui extends DonateMenuGui {

    public QuitMessagesGui(Player player) {
        super(player);
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(
                this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(DonateMenuGui.class, this.player).open(),
                40);
        int index = 0;
        for (QuitMessage quitMessage : QuitMessage.values()) {
            if (quitMessage == QuitMessage.NONE) {
                continue;
            }
            if (gamer.hasChildGroup(quitMessage.getGroup())) {
                if (gamer.getQuitMessage() == quitMessage) {
                    inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getYellowHead())
                            .setName(Lang.getMessage("QUIT_MESSAGE_SELECTED_GUI_ITEM_NAME", quitMessage.getID()))
                            .setLore(Lang.getList("QUIT_MESSAGE_SELECTED_GUI_ITEM_LORE", String.format(quitMessage.getMessage(), gamer.getDisplayName())))
                            .build()));
                } else {
                    inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getAcceptHead())
                            .setName(Lang.getMessage("QUIT_MESSAGE_ACCESS_GUI_ITEM_NAME", quitMessage.getID()))
                            .setLore(Lang.getList("QUIT_MESSAGE_ACCESS_GUI_ITEM_LORE", String.format(quitMessage.getMessage(), gamer.getDisplayName())))
                            .build(),
                            ((clicker, clickType, slot) -> {
                                if (Cooldown.hasCooldown(gamer.getName(), "QUIT_MESSAGE_SELECT")) {
                                    gamer.sendMessageLocale("QUIT_MESSAGE_SELECT_COOLDOWN", Cooldown.getCooldownLeft(gamer.getName(), "QUIT_MESSAGE_SELECT"));
                                    return;
                                }
                                gamer.setQuitMessage(quitMessage);
                                BukkitUtil.callEvent(new GamerChangeCustomizationEvent(gamer, CustomizationType.QUIT_MESSAGE));
                                gamer.sendMessageLocale("QUIT_MESSAGE_SELECT", quitMessage.getID());
                                setStaticItems();
                                Cooldown.addCooldown(gamer.getName(), "QUIT_MESSAGE_SELECT", 60);
                            })));
                }
            } else {
                inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getDenyHead())
                        .setName(Lang.getMessage("QUIT_MESSAGE_DENY_GUI_ITEM_NAME", quitMessage.getID()))
                        .setLore(Lang.getList("QUIT_MESSAGE_DENY_GUI_ITEM_LORE", String.format(quitMessage.getMessage(), gamer.getDisplayName()), quitMessage.getGroup().getChatPrefix()))
                        .build()));
            }
            index++;
        }
    }
}
