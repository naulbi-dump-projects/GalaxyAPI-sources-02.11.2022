package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.Packet;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.GPacketEntity;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.GPacketBase;

import java.beans.ConstructorProperties;

public abstract class GPacketEntityBase<A extends Packet<?>, T extends GEntity> extends GPacketBase<A> implements GPacketEntity<T> {

    protected T entity;
    
    @Override
    public void setEntity(T entity) {
        this.entity = entity;
        this.init();
    }
    
    @ConstructorProperties({ "entity" })
    public GPacketEntityBase(T entity) {
        this.entity = entity;
    }
    
    @Override
    public T getEntity() {
        return entity;
    }
}
