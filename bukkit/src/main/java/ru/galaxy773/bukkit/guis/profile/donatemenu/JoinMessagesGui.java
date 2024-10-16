package ru.galaxy773.bukkit.guis.profile.donatemenu;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.multiplatform.api.gamer.customization.JoinMessage;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

public class JoinMessagesGui extends DonateMenuGui {

    public JoinMessagesGui(Player player) {
        super(player);
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(
                this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(DonateMenuGui.class, this.player).open(),
                40);
        int index = 0;
        for (JoinMessage joinMessage : JoinMessage.values()) {
            if (joinMessage == JoinMessage.NONE) {
                continue;
            }
            if (gamer.hasChildGroup(joinMessage.getGroup())) {
                if (gamer.getJoinMessage() == joinMessage) {
                    inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getYellowHead())
                            .setName(Lang.getMessage("JOIN_MESSAGE_SELECTED_GUI_ITEM_NAME", joinMessage.getID()))
                            .setLore(Lang.getList("JOIN_MESSAGE_SELECTED_GUI_ITEM_LORE", String.format(joinMessage.getMessage(), gamer.getDisplayName())))
                            .build()));
                } else {
                    inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getAcceptHead())
                            .setName(Lang.getMessage("JOIN_MESSAGE_ACCESS_GUI_ITEM_NAME", joinMessage.getID()))
                            .setLore(Lang.getList("JOIN_MESSAGE_ACCESS_GUI_ITEM_LORE", String.format(joinMessage.getMessage(), gamer.getDisplayName())))
                            .build(),
                            ((clicker, clickType, slot) -> {
                                if (Cooldown.hasCooldown(gamer.getName(), "JOIN_MESSAGE_SELECT")) {
                                    gamer.sendMessageLocale("JOIN_MESSAGE_SELECT_COOLDOWN", Cooldown.getCooldownLeft(gamer.getName(), "JOIN_MESSAGE_SELECT"));
                                    return;
                                }
                                gamer.setJoinMessage(joinMessage);
                                BukkitUtil.callEvent(new GamerChangeCustomizationEvent(gamer, CustomizationType.JOIN_MESSAGE));
                                gamer.sendMessageLocale("JOIN_MESSAGE_SELECT", joinMessage.getID());
                                setStaticItems();
                                Cooldown.addCooldown(gamer.getName(), "JOIN_MESSAGE_SELECT", 60);
                            })));
                }
            } else {
                inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getDenyHead())
                        .setName(Lang.getMessage("JOIN_MESSAGE_DENY_GUI_ITEM_NAME", joinMessage.getID()))
                        .setLore(Lang.getList("JOIN_MESSAGE_DENY_GUI_ITEM_LORE", String.format(joinMessage.getMessage(), gamer.getDisplayName()), joinMessage.getGroup().getChatPrefix()))
                        .build()));
            }
            index++;
        }
    }
}
