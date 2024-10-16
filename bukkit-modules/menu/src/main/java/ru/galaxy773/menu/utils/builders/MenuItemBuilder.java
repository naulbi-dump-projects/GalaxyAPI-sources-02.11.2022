package ru.galaxy773.menu.utils.builders;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;
import ru.galaxy773.menu.api.menu.MenuItem;
import ru.galaxy773.menu.object.CraftMenuItem;
import ru.galaxy773.multiplatform.api.utils.builders.Builder;

import java.util.function.Function;

public class MenuItemBuilder implements Builder<MenuItem> {

    private ItemStack itemStack;
    private Function<BukkitGamer, ItemStack> dynamicItemStack;
    private ClickAction clickAction;
    private int slot;

    public static MenuItemBuilder builder() {
        return new MenuItemBuilder();
    }

    public MenuItemBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public MenuItemBuilder setDynamicItemStack(Function<BukkitGamer, ItemStack> dynamicItemStack) {
        this.dynamicItemStack = dynamicItemStack;
        return this;
    }

    public MenuItemBuilder setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
        return this;
    }

    public MenuItemBuilder setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public MenuItemBuilder clone() {
        MenuItemBuilder menuItemBuilder = MenuItemBuilder.builder();
        menuItemBuilder.itemStack = this.itemStack;
        menuItemBuilder.dynamicItemStack = this.dynamicItemStack;
        menuItemBuilder.clickAction = this.clickAction;
        menuItemBuilder.slot = this.slot;
        return menuItemBuilder;
    }

    @Override
    public MenuItem build() {
        return this.dynamicItemStack == null ? new CraftMenuItem(this.itemStack, this.clickAction, this.slot) : new CraftMenuItem(this.dynamicItemStack, this.clickAction, this.slot);
    }
}

