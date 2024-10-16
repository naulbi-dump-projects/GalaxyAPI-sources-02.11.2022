package ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.AbstractPacket;

public class WrapperPlayServerEntityHeadRotation extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_HEAD_ROTATION;

    public WrapperPlayServerEntityHeadRotation() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerEntityHeadRotation(PacketContainer packet) {
        super(packet, TYPE);
    }

    public int getEntityID() {
        return handle.getIntegers().read(0);
    }

    public void setEntityID(int value) {
        handle.getIntegers().write(0, value);
    }

    public Entity getEntity(World world) {
        return handle.getEntityModifier(world).read(0);
    }

    public byte getHeadYaw() {
        return handle.getBytes().read(0);
    }

    public void setHeadYaw(byte value) {
        handle.getBytes().write(0, value);
    }
}
