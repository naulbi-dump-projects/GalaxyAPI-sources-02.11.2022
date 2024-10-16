package ru.galaxy773.bukkit.nms.v1_12_R1.gui;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.nms.interfaces.gui.GEnchantingTable;
import ru.galaxy773.bukkit.nms.types.EnchantingSlot;

public class GEnchantingTableImpl implements GEnchantingTable {

    private EntityPlayer entityPlayer;
    private EnchantingTableContainer container;
    private String title;
    
    public GEnchantingTableImpl(Player player) {
        this.entityPlayer = ((CraftPlayer)player).getHandle();
        this.container = new EnchantingTableContainer(this.entityPlayer);
    }

    @Override
    public void addItem(EnchantingSlot slot, ItemStack stack) {
        this.container.setItem(slot.getSlot(), CraftItemStack.asNMSCopy(stack));
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void openGui() {
        if (entityPlayer == null)
            return;

        int c = this.entityPlayer.nextContainerCounter();

        this.entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(c, "minecraft:enchanting_table", new ChatMessage("Стол зачарований")));
        this.entityPlayer.activeContainer = this.container;
        this.entityPlayer.activeContainer.windowId = c;
        this.entityPlayer.activeContainer.addSlotListener(this.entityPlayer);
    }

    private class EnchantingTableContainer extends ContainerEnchantTable {

        EnchantingTableContainer(EntityHuman entity) {
            super(entity.inventory, entity.world, new BlockPosition(0, 0, 0));
        }

        @Override
        public boolean canUse(EntityHuman entityhuman) {
            return true;
        }
    }
}
