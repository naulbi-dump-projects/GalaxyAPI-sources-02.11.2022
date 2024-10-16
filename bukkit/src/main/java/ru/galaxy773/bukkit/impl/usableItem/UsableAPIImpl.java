package ru.galaxy773.bukkit.impl.usableItem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.usableitem.ClickAction;
import ru.galaxy773.bukkit.api.usableitem.UsableAPI;
import ru.galaxy773.bukkit.api.usableitem.UsableItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsableAPIImpl implements UsableAPI {

    private final BukkitPlugin BukkitPlugin;

    private UsablesManager manager;

    public UsableAPIImpl(BukkitPlugin BukkitPlugin) {
        this.BukkitPlugin = BukkitPlugin;
    }

    @Override
    public UsableItem createUsableItem(ItemStack itemStack, Player owner, ClickAction clickAction) {
        return create(itemStack, owner, clickAction);
    }

    @Override
    public UsableItem createUsableItem(ItemStack itemStack, ClickAction clickAction) {
        return create(itemStack, null, clickAction);
    }

    @Override
    public void removeItem(UsableItem item) {
        item.remove();
    }

    @Override
    public List<UsableItem> getUsableItems() {
        if (manager == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(manager.getUsableItems().values());
    }


    private UsableItem create(ItemStack itemStack, Player owner, ClickAction clickAction) {
        if (manager == null) {
            manager = new UsablesManager(BukkitPlugin);
        }

        return new CraftUsableItem(itemStack, owner, clickAction, manager);
    }
}
