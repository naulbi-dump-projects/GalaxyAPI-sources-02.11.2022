package ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.AbstractPacket;

public class WrapperPlayServerEntityStatus extends AbstractPacket {
    public static final PacketType TYPE = Server.ENTITY_STATUS;

    public WrapperPlayServerEntityStatus() {
        super(new PacketContainer(TYPE), TYPE);
        this.handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerEntityStatus(PacketContainer packet) {
        super(packet, TYPE);
    }

    public int getEntityID() {
        return this.handle.getIntegers().read(0);
    }

    public void setEntityID(int value) {
        this.handle.getIntegers().write(0, value);
    }

    public Entity getEntity(World world) {
        return this.handle.getEntityModifier(world).read(0);
    }

    public Entity getEntity(PacketEvent event) {
        return this.getEntity(event.getPlayer().getWorld());
    }

    public byte getEntityStatus() {
        return this.handle.getBytes().read(0);
    }

    public void setEntityStatus(byte value) {
        this.handle.getBytes().write(0, value);
    }
}
