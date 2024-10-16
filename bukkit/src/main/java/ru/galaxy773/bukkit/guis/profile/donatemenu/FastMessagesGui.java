package ru.galaxy773.bukkit.guis.profile.donatemenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.multiplatform.api.gamer.customization.FastMessage;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

public class FastMessagesGui extends DonateMenuGui {

    public FastMessagesGui(Player player) {
        super(player);
        createInventory();
    }

    @Override
    protected void setStaticItems() {
        INVENTORY_API.backButton(
                this.inventory,
                (clicker, clickType, slot) -> GUI_MANAGER.getGui(DonateMenuGui.class, this.player).open(),
                40);
        int index = 0;
        for (FastMessage fastMessage : FastMessage.values()) {
            if (gamer.hasChildGroup(fastMessage.getGroup())) {
                inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getAcceptHead())
                        .setName(Lang.getMessage("FAST_MESSAGE_ACCESS_GUI_ITEM_NAME", index + 1))
                        .setLore(Lang.getList("FAST_MESSAGE_ACCESS_GUI_ITEM_LORE", fastMessage.getMessage()))
                        .build(),
                        ((clicker, clickType, slot) -> {
                            if (Cooldown.hasCooldown(clicker.getName(), "FAST_MESSAGE")) {
                                return;
                            }
                            clicker.chat(clickType == ClickType.RIGHT ? "!" + fastMessage.getMessage() : fastMessage.getMessage());
                            Cooldown.addCooldown(clicker.getName(), "FAST_MESSAGE", 20);
                        })));
            } else {
                inventory.setItem(messagesSlots[index], new GItem(ItemBuilder.builder(HeadUtil.getDenyHead())
                        .setName(Lang.getMessage("FAST_MESSAGE_DENY_GUI_ITEM_NAME", index + 1))
                        .setLore(Lang.getList("FAST_MESSAGE_DENY_GUI_ITEM_LORE", fastMessage.getMessage(), fastMessage.getGroup().getChatPrefix()))
                        .build()));
            }
            index++;
        }
    }
}
