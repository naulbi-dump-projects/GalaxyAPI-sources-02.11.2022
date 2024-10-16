package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.utils.version.ViaVersionUtil;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityArmorStand;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketSpawnEntityLiving;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityLivingBase;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

public class PacketSpawnEntityLivingImpl extends GPacketEntityBase<PacketPlayOutSpawnEntityLiving, GEntityLiving>
        implements PacketSpawnEntityLiving {

    private static final double Y = 0.8;

    private Version versionSender = Version.EMPTY;

    public PacketSpawnEntityLivingImpl(GEntityLiving entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutSpawnEntityLiving init() {
        Location location = entity.getLocation();

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(
                ((GEntityLivingBase<?>)entity).getEntityNms());

        if (this.versionSender.getVersion() >= Version.MINECRAFT_1_11.getVersion() - 1 && entity.hasPassenger())
            ReflectionUtil.setFieldValue(packet, "e", location.getY() + Y);
        if (this.versionSender.getVersion() >= Version.MINECRAFT_1_9.getVersion()
                && this.versionSender.getVersion() <= Version.MINECRAFT_1_10.getVersion()
                && !entity.hasPassenger()) {
            if (!(entity instanceof GEntityArmorStand))
                ReflectionUtil.setFieldValue(packet, "e", location.getY() - Y - 0.1);

            GEntityArmorStand entityArmorStand = (GEntityArmorStand) entity;
            if (entityArmorStand.isInvisible())
                ReflectionUtil.setFieldValue(packet, "e", location.getY() - Y - 0.1);
        }


        return packet;
    }

    @Override
    public void sendPacket(Player player) {
        fixLocation(player);
        super.sendPacket(player);
    }

    private void fixLocation(Player player) {
    	this.versionSender = ViaVersionUtil.getVersion(player);
    }
}
