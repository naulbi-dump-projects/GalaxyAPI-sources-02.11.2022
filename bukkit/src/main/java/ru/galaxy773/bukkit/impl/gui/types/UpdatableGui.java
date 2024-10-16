package ru.galaxy773.bukkit.impl.gui.types;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.inventory.type.BaseInventory;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.inventory.type.ScrollInventory;

public abstract class UpdatableGui<T extends BaseInventory> extends AbstractGui<T> {

    @Getter
    private boolean opened;

    public UpdatableGui(Player player) {
        super(player);
        this.guiType = GuiType.DYNAMIC;
    }

    protected abstract void setDynamicItems();

    public void setActions() {
        this.inventory.setOpenAction(opener -> opened = true);
        this.inventory.setCloseAction(closer -> opened = false);
    }

    public void update() {
        if (this.player == null || !this.player.isOnline()) {
            return;
        }
        if (this.inventory == null) {
            return;
        }
        this.setDynamicItems();
    }

    @Override
    public void open() {
        if (this.player == null || !this.player.isOnline()) {
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
        this.setDynamicItems();
        this.inventory.openInventory(this.player);
    }
}
