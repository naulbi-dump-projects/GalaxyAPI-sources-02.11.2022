package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityDestroy;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.GPacketBase;

public class PacketEntityDestroyImpl extends GPacketBase<PacketPlayOutEntityDestroy> implements PacketEntityDestroy {

    private int[] entityIDs;

    public PacketEntityDestroyImpl(int... entityIDs) {
        this.entityIDs = entityIDs;
    }

    @Override
    protected PacketPlayOutEntityDestroy init() {
        return new PacketPlayOutEntityDestroy(entityIDs);
    }
}
