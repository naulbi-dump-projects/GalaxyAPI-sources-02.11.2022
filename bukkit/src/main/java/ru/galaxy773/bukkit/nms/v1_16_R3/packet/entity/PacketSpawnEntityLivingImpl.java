package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityArmorStand;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketSpawnEntityLiving;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.GEntityLivingBase;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

public class PacketSpawnEntityLivingImpl extends GPacketEntityBase<PacketPlayOutSpawnEntityLiving, GEntityLiving>
        implements PacketSpawnEntityLiving {

    private Version versionSender;
    
    public PacketSpawnEntityLivingImpl(GEntityLiving entity) {
        super(entity);
        this.versionSender = Version.EMPTY;
    }
    
    protected PacketPlayOutSpawnEntityLiving init() {
        Location location = entity.getLocation();
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(((GEntityLivingBase)entity).getEntityNms());
        if (versionSender.getVersion() >= Version.MINECRAFT_1_11.getVersion() - 1 && entity.hasPassenger()) {
            ReflectionUtil.setFieldValue(packet, "e", location.getY() + 0.8);
        }
        if (versionSender.getVersion() >= Version.MINECRAFT_1_9.getVersion() && this.versionSender.getVersion() <= Version.MINECRAFT_1_10.getVersion() && !entity.hasPassenger()) {
            if (!(entity instanceof GEntityArmorStand)) {
                ReflectionUtil.setFieldValue(packet, "e", location.getY() - 0.8 - 0.1);
            }
            if (((GEntityArmorStand)entity).isInvisible()) {
                ReflectionUtil.setFieldValue(packet, "e", location.getY() - 0.8 - 0.1);
            }
        }
        return packet;
    }
    
    @Override
    public void sendPacket(Player player) {
        fixLocation(player);
        super.sendPacket(player);
        NmsAPI.getManager().sendMetaData(player, entity.getBukkitEntity());
    }

    private void fixLocation(Player player) {
        //проверять с какой версии
        BukkitGamer gamer = GamerManager.getGamer(player);
        this.versionSender = Version.EMPTY;
        if (gamer == null)
            return;

        this.versionSender = gamer.getVersion();
    }
}
