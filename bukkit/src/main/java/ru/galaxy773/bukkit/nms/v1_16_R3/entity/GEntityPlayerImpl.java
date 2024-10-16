package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;

public class GEntityPlayerImpl extends GEntityLivingBase<EntityPlayer> implements GEntityPlayer {

    public GEntityPlayerImpl(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile,
                             PlayerInteractManager playerinteractmanager) {
        super(new EntityPlayer(minecraftserver, worldserver, gameprofile, playerinteractmanager));
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    @Override
    public GameProfile getProfile() {
        return entity.getProfile();
    }

    @Override
    public int getPing() {
        return entity.ping;
    }
}
