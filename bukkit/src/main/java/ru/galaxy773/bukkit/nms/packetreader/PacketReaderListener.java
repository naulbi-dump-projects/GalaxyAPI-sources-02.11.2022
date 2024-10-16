package ru.galaxy773.bukkit.nms.packetreader;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.event.player.AsyncChunkSendEvent;
import ru.galaxy773.bukkit.api.event.player.AsyncPlayerInUseEntityEvent;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

public class PacketReaderListener extends PacketAdapter {

    public PacketReaderListener() {
        super(BukkitPlugin.getInstance(), ListenerPriority.NORMAL,
                PacketType.Play.Server.MAP_CHUNK,
                PacketType.Play.Client.USE_ENTITY
        );
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        Player player = e.getPlayer();
        PacketContainer packet = e.getPacket();
        if (packet.getType() == PacketType.Play.Server.MAP_CHUNK) {
            int x = packet.getIntegers().read(0);
            int z = packet.getIntegers().read(1);
            try {
                String worldName = player.getWorld().getName();
                AsyncChunkSendEvent event = new AsyncChunkSendEvent(player, worldName, x, z);
                BukkitUtil.callEvent(event);
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        Player player = e.getPlayer();
        PacketContainer packet = e.getPacket();
        if (player == null) {
            return;
        }
        if (packet.getType() == PacketType.Play.Client.USE_ENTITY) {
            if (Cooldown.hasCooldown(player.getName(), "playInUse")) {
                return;
            }
            Cooldown.addCooldown(player.getName(), "playInUse", 10L);
            int entityId = packet.getIntegers().read(0);
            EnumWrappers.EntityUseAction entityUseAction = packet.getEntityUseActions().read(0);
            EnumWrappers.Hand hand = EnumWrappers.Hand.MAIN_HAND;
            if (entityUseAction != EnumWrappers.EntityUseAction.ATTACK) {
                hand = packet.getHands().read(0);
            }
            AsyncPlayerInUseEntityEvent event = new AsyncPlayerInUseEntityEvent(player, entityId,
                    AsyncPlayerInUseEntityEvent.Action.valueOf(entityUseAction.name()),
                    AsyncPlayerInUseEntityEvent.Hand.valueOf(hand.name()));
            BukkitUtil.callEvent(event);
        }
    }
}
