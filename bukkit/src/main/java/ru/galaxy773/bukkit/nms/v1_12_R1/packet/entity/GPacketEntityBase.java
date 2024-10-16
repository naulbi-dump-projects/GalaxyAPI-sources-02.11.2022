package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.Packet;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.GPacketEntity;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.GPacketBase;

@AllArgsConstructor
@Getter
public abstract class GPacketEntityBase<A extends Packet<?>, T extends GEntity> extends GPacketBase<A>
        implements GPacketEntity<T> {

    protected T entity;

    @Override
    public void setEntity(T entity) {
        this.entity = entity;
        init();
    }
}