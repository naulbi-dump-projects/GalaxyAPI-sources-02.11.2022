package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import ru.galaxy773.bukkit.api.entity.npc.AnimationNpcType;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketAnimation;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;

@Getter
public class PacketAnimationImpl extends GPacketEntityBase<PacketPlayOutAnimation, GEntity> implements PacketAnimation {

    private AnimationNpcType animation;

    public PacketAnimationImpl(GEntity entity, AnimationNpcType animation) {
        super(entity);
        this.animation = animation;
    }

    @Override
    public void setAnimation(AnimationNpcType animation) {
        this.animation = animation;
        init();
    }

    @Override
    protected PacketPlayOutAnimation init() {
        return new PacketPlayOutAnimation(((GEntityBase<?>)entity).getEntityNms(), animation.ordinal());
    }
}
