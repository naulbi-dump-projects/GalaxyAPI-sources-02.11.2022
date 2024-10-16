package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityTeleport;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.GEntityBase;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

public class PacketEntityTeleportImpl extends GPacketEntityBase<PacketPlayOutEntityTeleport, GEntity>
        implements PacketEntityTeleport {

    private Version versionSender;
    
    public PacketEntityTeleportImpl(final GEntity entity) {
        super(entity);
        this.versionSender = Version.EMPTY;
    }
    
    protected PacketPlayOutEntityTeleport init() {
        final Location location = entity.getLocation();
        final PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(((GEntityBase)entity).getEntityNms());
        if (versionSender.getVersion() >= Version.MINECRAFT_1_11.getVersion() - 1 && entity.hasPassenger()) {
            ReflectionUtil.setFieldValue(packet, "c", location.getY() + 0.8);
        }
        if (versionSender.getVersion() >= Version.MINECRAFT_1_9.getVersion() && versionSender.getVersion() <= Version.MINECRAFT_1_10.getVersion() && !entity.hasPassenger()) {
            ReflectionUtil.setFieldValue(packet, "c", location.getY() - 0.8 - 0.1);
        }
        return packet;
    }
    
    @Override
    public void sendPacket(Player player) {
        this.fixLocation(player);
        super.sendPacket(player);
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
