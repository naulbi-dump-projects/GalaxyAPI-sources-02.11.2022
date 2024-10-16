package ru.galaxy773.bukkit.api.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;

public class GItem {

    @Setter
    @Getter
    private ItemStack item;
    @Setter
    @Getter
    private ClickAction clickAction;
    @Getter
    private boolean mutable;

    public GItem(ItemStack itemStack, ClickAction clickAction) {
        this.item = itemStack;
        this.clickAction = clickAction;
        this.mutable = false;
    }

    public GItem(ItemStack itemStack) {
        this(itemStack, (player, clickType, slot) -> {
        });
    }

    public GItem setMutable(boolean mutable) {
        this.mutable = mutable;
        return this;
    }
}
