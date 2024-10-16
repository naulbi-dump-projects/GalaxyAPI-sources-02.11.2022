package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.utils.version.ViaVersionUtil;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityTeleport;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

public class PacketEntityTeleportImpl extends GPacketEntityBase<PacketPlayOutEntityTeleport, GEntity>
        implements PacketEntityTeleport {

    private static final double Y = 0.8;

    private Version versionSender = Version.EMPTY;

    public PacketEntityTeleportImpl(GEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutEntityTeleport init() {
        Location location = entity.getLocation();
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(((GEntityBase<?>)entity).getEntityNms());

        if (this.versionSender.getVersion() >= Version.MINECRAFT_1_11.getVersion() - 1 && entity.hasPassenger())
            ReflectionUtil.setFieldValue(packet, "c", location.getY() + Y);
        if (this.versionSender.getVersion() >= Version.MINECRAFT_1_9.getVersion()
                && this.versionSender.getVersion() <= Version.MINECRAFT_1_10.getVersion()
                && !entity.hasPassenger())
            ReflectionUtil.setFieldValue(packet, "c", location.getY() - Y - 0.1);

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
