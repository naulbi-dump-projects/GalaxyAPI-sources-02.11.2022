package ru.galaxy773.menu.api.menu;

import java.util.Collection;
import java.util.function.Consumer;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;

public interface Menu {

    String getTitle();

    GInventory getInventory();

    int getRows();

    MenuItem getItem(int var1);

    Collection<MenuItem> getItems();

    void addItem(MenuItem var1);

    void addItems(Collection<MenuItem> var1);

    void removeItem(int var1);

    void setOpenAction(Consumer<Player> var1);

    void setCloseAction(Consumer<Player> var1);

    void open(BukkitGamer gamer);
}

