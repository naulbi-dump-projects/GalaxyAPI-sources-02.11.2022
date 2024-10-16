package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.DragonControllerPhase;
import net.minecraft.server.v1_16_R3.EntityEnderDragon;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.api.entity.npc.types.EnderDragonNPC;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityDragon;

public class GEntityDragonImpl extends GEntityLivingBase<EntityEnderDragon> implements GEntityDragon {

    public GEntityDragonImpl(World world) {
        super(new EntityEnderDragon(EntityTypes.ENDER_DRAGON, world));
    }

    @Override
    public EnderDragonNPC.Phase getPhase() {
        return EnderDragonNPC.Phase.values()[entity.getDataWatcher().get(EntityEnderDragon.PHASE)];
    }

    @Override
    public void setPhase(EnderDragonNPC.Phase phase) {
        entity.getDragonControllerManager().setControllerPhase(getMinecraftPhase(phase));
    }

    private static DragonControllerPhase getMinecraftPhase(EnderDragonNPC.Phase phase) {
        return DragonControllerPhase.getById(phase.ordinal());
    }
}
