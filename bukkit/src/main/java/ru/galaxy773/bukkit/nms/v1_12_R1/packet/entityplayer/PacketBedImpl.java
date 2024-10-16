package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entityplayer;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBed;
import org.bukkit.Location;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketBed;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityPlayerImpl;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity.GPacketEntityBase;

public class PacketBedImpl extends GPacketEntityBase<PacketPlayOutBed, GEntityPlayer>
	implements PacketBed {

	private Location bedLocation;

	public PacketBedImpl(GEntityPlayer entity, Location bedLocation) {
		super(entity);
		this.bedLocation = bedLocation;
	}

	@Override
	protected PacketPlayOutBed init() {
		return new PacketPlayOutBed(((GEntityPlayerImpl)entity).getEntityNms(),
        new BlockPosition(bedLocation.getX(), bedLocation.getY(), bedLocation.getZ()));
	}
}
