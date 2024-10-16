package ru.galaxy773.bukkit.nms.v1_16_R3.gui;

import lombok.Setter;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.nms.interfaces.gui.GEnchantingTable;
import ru.galaxy773.bukkit.nms.types.EnchantingSlot;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class GEnchantingTableImpl implements GEnchantingTable {

    private final EntityPlayer entityPlayer;
    @Setter
    private String title;
    private final EnchantingTableContainer container;

    public GEnchantingTableImpl(Player player) {
        BukkitGamer gamer = GamerManager.getGamer(player);
        this.title = Lang.getMessage("ENCHANT_TITLE");
        this.entityPlayer = ((CraftPlayer) player).getHandle();
        this.container = new EnchantingTableContainer(entityPlayer);
    }

    @Override
    public void addItem(EnchantingSlot slot, ItemStack stack) {
        container.setItem(slot.getSlot(), CraftItemStack.asNMSCopy(stack));
    }

    @Override
    public void openGui() {
        if (entityPlayer == null) {
            return;
        }

        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(container.windowId, Containers.ENCHANTMENT,
                new ChatComponentText(title)));
        (entityPlayer.activeContainer = container).addSlotListener(entityPlayer);
    }

    private static class EnchantingTableContainer extends ContainerEnchantTable {
        EnchantingTableContainer(EntityPlayer player) {
            super(player.nextContainerCounter(), player.inventory);
        }

        public boolean canUse(EntityHuman entityhuman) {
            return true;
        }
    }
}