package ru.galaxy773.bukkit.api.inventory.type;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

import java.util.function.Consumer;

public interface BaseInventory extends InventoryHolder {

    void openInventory(BukkitGamer gamer);

    void openInventory(Player player);

    void setOpenAction(Consumer<Player> openAction);

    void setCloseAction(Consumer<Player> closeAction);

    Consumer<Player> getOpenAction();

    Consumer<Player> getCloseAction();

    Inventory getHandle();

    @Override
    default Inventory getInventory() {
        return this.getHandle();
    }
}
