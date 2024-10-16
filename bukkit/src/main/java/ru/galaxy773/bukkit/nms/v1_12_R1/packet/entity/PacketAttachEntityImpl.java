package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutAttachEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketAttachEntity;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;

@Getter
public class PacketAttachEntityImpl extends GPacketEntityBase<PacketPlayOutAttachEntity, GEntity> implements PacketAttachEntity {

    private GEntity vehicle;

    public PacketAttachEntityImpl(GEntity entity, GEntity vehicle) {
        super(entity);
        this.vehicle = vehicle;
    }

    @Override
    public void setVehicle(GEntity vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    protected PacketPlayOutAttachEntity init() {
        return new PacketPlayOutAttachEntity(((GEntityBase<?>)entity).getEntityNms(),
                ((GEntityBase<?>)vehicle).getEntityNms());
    }
}
