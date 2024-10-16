package ru.galaxy773.bukkit.nms.v1_12_R1.gui;

import net.minecraft.server.v1_12_R1.*;
import ru.galaxy773.bukkit.nms.interfaces.gui.GAnvil;

public class GAnvilImpl extends ContainerAnvil implements GAnvil {

    private EntityPlayer entityPlayer;

    public GAnvilImpl(EntityPlayer entityPlayer) {
        super(entityPlayer.inventory, entityPlayer.world,
                new BlockPosition(0, 0, 0), entityPlayer);
        this.entityPlayer = entityPlayer;
    }

    @Override
    public void openGui() {
        int containerId = entityPlayer.nextContainerCounter();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(containerId, "minecraft:anvil",
                new ChatMessage("Наковальня"));
        entityPlayer.playerConnection.sendPacket(packet);
        entityPlayer.activeContainer = this;
        entityPlayer.activeContainer.windowId = containerId;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
    }

    @Override
    public boolean canUse(EntityHuman entityhuman) {
        return true;
    }
}
