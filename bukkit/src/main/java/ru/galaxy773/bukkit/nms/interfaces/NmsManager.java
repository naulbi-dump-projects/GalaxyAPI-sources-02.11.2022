package ru.galaxy773.bukkit.nms.interfaces;

import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.gui.GAnvil;
import ru.galaxy773.bukkit.nms.interfaces.gui.GEnchantingTable;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface NmsManager {

    <T extends GEntity> T createGEntity(Class<T> classEntity, Location location);

    List<Block> getBlocksBelow(Player player);

    GEntityPlayer createEntityPlayer(World world, GameProfile gameProfile);

    GWorldBorder createBorder(World world);
    
    void updateScaledHealth(Player player);

    void triggerHealthUpdate(Player player);

    void updateAbilities(Player player);

    int getEntityID(Entity entity);

    GameProfile getGameProfile(Player player);

    int getItemID(ItemStack itemStack);

    int getPing(Player player);

    GAnvil getAnvil(Player player);

    GEnchantingTable getEnchantingTable(Player player);

    void setSkin(Player player, String value, String signature);
    
    void sendRespawn(Player player);
    
    void sendCrashClientPacket(Player target);

    Channel getChannel(Player player);

    void sendPacket(Player player, Object packet);
    
    void disableFire(Player player);

    PacketContainer getPacketContainer();

    void playChestAnimation(Block chest, boolean open);

    void removeArrowFromPlayer(Player player);

    void stopReadingPacket(Player player);

    void sendMetaData(Player player, Entity entity);
    void sendGlowing(Player player, Entity who, boolean glow);

    void launchInstantFirework(Firework firework);
}
