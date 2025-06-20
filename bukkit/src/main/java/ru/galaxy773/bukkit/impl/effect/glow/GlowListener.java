package ru.galaxy773.bukkit.impl.effect.glow;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.effect.PlayerGlowing;
import ru.galaxy773.bukkit.impl.effect.ParticleAPIImpl;

import java.util.List;

public class GlowListener extends PacketAdapter implements Listener {

    private final GlowManager glowManager;

    public GlowListener(ParticleAPIImpl particleAPI) {
        super(BukkitPlugin.getInstance(),
                PacketType.Play.Server.ENTITY_METADATA,
                PacketType.Play.Server.NAMED_ENTITY_SPAWN
        );

        this.glowManager = particleAPI.getGlowManager();

        Bukkit.getPluginManager().registerEvents(this, BukkitPlugin.getInstance());
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (player == null) {
            return;
        }
        String name = player.getName();
        glowManager.getGlowings().remove(name);

        PlayerGlowing memberGlowing = glowManager.getMemberGlowing().remove(name);
        if (memberGlowing == null) {
            return;
        }

        memberGlowing.removeEntity(player);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.isPlayerTemporary()) {
            return;
        }

        Player player = e.getPlayer();

        PacketContainer packet = e.getPacket();

        Entity otherEntity = packet.getEntityModifier(player.getWorld()).read(0);
        if (player == otherEntity) {
            return;
        }

        Byte input = getInput(packet);
        if (input == null) {
            return;
        }

        byte filtered = filterFlag(player, otherEntity, input);
        if (filtered == input) {
            return;
        }

        packet = packet.deepClone();

        if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {
            WrappedDataWatcher watcher = new WrappedDataWatcher(packet.getWatchableCollectionModifier().read(0));
            watcher.setObject(0, filtered);
            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        } else {
            WrappedDataWatcher watcherClone = packet.getDataWatcherModifier().read(0).deepClone();
            watcherClone.setObject(0, filtered);
            packet.getDataWatcherModifier().write(0, watcherClone);
        }

        e.setPacket(packet);
    }

    private Byte getInput(PacketContainer packet) {
        List<WrappedWatchableObject> metadata;
        if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {
            metadata = packet.getWatchableCollectionModifier().read(0);
        } else {
            metadata = packet.getDataWatcherModifier().read(0).getWatchableObjects();
        }

        for (WrappedWatchableObject object : metadata) {
            if (object.getIndex() == 0) {
                return (Byte) object.getValue();
            }
        }

        return null;
    }

    private byte filterFlag(Player player, Entity entity, Byte input) {
        if (!(entity instanceof Player)) {
            return input;
        }

        CraftPlayerGlowing craftPlayerGlowing = glowManager.getGlowings().get(player.getName());
        if (craftPlayerGlowing == null || !craftPlayerGlowing.getGlowingsEntity().contains(entity.getName())) {
            return input;
        }

        return (byte) (input | 1 << 6);
    }
}
