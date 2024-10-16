package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import ru.galaxy773.bukkit.api.entity.npc.AnimationNpcType;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketAnimation;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.GEntityBase;

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
    
    protected PacketPlayOutAnimation init() {
        return new PacketPlayOutAnimation(((GEntityBase)entity).getEntityNms(), animation.ordinal());
    }
    
    @Override
    public AnimationNpcType getAnimation() {
        return animation;
    }
}
