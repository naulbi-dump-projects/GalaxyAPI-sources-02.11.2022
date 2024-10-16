package ru.galaxy773.menu.object;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;
import ru.galaxy773.menu.api.menu.MenuItem;

import java.util.function.Function;

public class CraftMenuItem implements MenuItem {

    private ItemStack itemStack;
    private Function<BukkitGamer, ItemStack> dynamicItemStack;
    private ClickAction clickAction;
    private int slot;

    public CraftMenuItem(ItemStack itemStack, ClickAction clickAction, int slot) {
        this.itemStack = itemStack;
        this.clickAction = clickAction;
        this.slot = slot;
    }

    public CraftMenuItem(Function<BukkitGamer, ItemStack> dynamicItemStack, ClickAction clickAction, int slot) {
        this.dynamicItemStack = dynamicItemStack;
        this.clickAction = clickAction;
        this.slot = slot;
    }

    @Override
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @Override
    public ItemStack getDynamicItemStack(BukkitGamer gamer) {
        return this.dynamicItemStack != null ? this.dynamicItemStack.apply(gamer) : this.getItemStack();
    }

    @Override
    public boolean isDynamic() {
        return this.dynamicItemStack != null;
    }

    @Override
    public ClickAction getClickAction() {
        return this.clickAction;
    }

    @Override
    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }

    @Override
    public int getSlot() {
        return this.slot;
    }

    @Override
    public void setSlot(int slot) {
        this.slot = slot;
    }
}

