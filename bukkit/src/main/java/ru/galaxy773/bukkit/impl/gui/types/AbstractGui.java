package ru.galaxy773.bukkit.impl.gui.types;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.inventory.type.BaseInventory;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.inventory.type.ScrollInventory;

public abstract class AbstractGui<T extends BaseInventory> {

    protected static final InventoryAPI INVENTORY_API = BukkitAPI.getInventoryAPI();
    protected static final GuiManager<AbstractGui<?>> GUI_MANAGER = BukkitAPI.getGuiManager();

    protected final Player player;
    protected final BukkitGamer gamer;
    @Getter
    protected T inventory;
    @Getter
    protected GuiType guiType = GuiType.STATIC;
    @Getter
    @Setter
    protected boolean recreateOnOpen;

    public AbstractGui(Player player) {
        this.player = player;
        this.gamer = GamerManager.getGamer(player);
    }

    protected abstract void createInventory();

    protected abstract void setStaticItems();

    public void open() {
        if (this.player == null || !player.isOnline()) {
            return;
        }
        if (this.recreateOnOpen) {
            this.createInventory();
        }
        if (this.inventory == null) {
            return;
        }
        if (!this.recreateOnOpen) {
            if (inventory instanceof MultiInventory) {
                ((MultiInventory) inventory).clearInventories();
            } else if (inventory instanceof ScrollInventory) {
                ((ScrollInventory) inventory).clearInventory();
            } else if (inventory instanceof GInventory) {
                ((GInventory) inventory).clearInventory();
            }
        }
        this.setStaticItems();
        this.inventory.openInventory(this.player);
    }
}
