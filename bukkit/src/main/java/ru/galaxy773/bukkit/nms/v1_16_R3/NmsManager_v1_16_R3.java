package ru.galaxy773.bukkit.nms.v1_16_R3;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.netty.channel.Channel;
import ru.galaxy773.bukkit.nms.interfaces.GWorldBorder;
import ru.galaxy773.bukkit.nms.interfaces.gui.GAnvil;
import ru.galaxy773.bukkit.nms.interfaces.gui.GEnchantingTable;
import ru.galaxy773.bukkit.nms.v1_16_R3.gui.GEnchantingTableImpl;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.*;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.*;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.PacketContainerImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class NmsManager_v1_16_R3 implements NmsManager {

    private final PacketContainer container = new PacketContainerImpl();

    @Override
    public <T extends GEntity> T createGEntity(Class<T> classEntity, Location location) {
        if (classEntity == GEntity.class || classEntity == GEntityLiving.class || classEntity == GEntityPlayer.class)
            throw new IllegalArgumentException("Type for entity not foun");
        WorldServer nmsWorld = ((CraftWorld)location.getWorld()).getHandle();
        GEntity entity = null;
        if (GEntityLiving.class.isAssignableFrom(classEntity)) {
            if (GEntityArmorStand.class.isAssignableFrom(classEntity)) {
                entity = new GEntityArmorStandImpl(nmsWorld);
            } else if (GEntityBlaze.class.isAssignableFrom(classEntity)) {
                entity = new GEntityBlazeImpl(nmsWorld);
            } else if (GEntityCow.class.isAssignableFrom(classEntity)) {
                entity = new GEntityCowImpl(nmsWorld);
            } else if (GEntityCreeper.class.isAssignableFrom(classEntity)) {
                entity = new GEntityCreeperImpl(nmsWorld);
            } else if (GEntityDragon.class.isAssignableFrom(classEntity)) {
                entity = new GEntityDragonImpl(nmsWorld);
            } else if (GEntityMushroomCow.class.isAssignableFrom(classEntity)) {
                entity = new GEntityMushroomCowImpl(nmsWorld);
            } else if (GEntitySlime.class.isAssignableFrom(classEntity)) {
                entity = new GEntitySlimeImpl(nmsWorld);
            } else if (GEntityVillager.class.isAssignableFrom(classEntity)) {
                entity = new GEntityVillagerImpl(nmsWorld);
            } else if (GEntityWolf.class.isAssignableFrom(classEntity)) {
                entity = new GEntityWolfImpl(nmsWorld);
            } else if (GEntityZombie.class.isAssignableFrom(classEntity)) {
                entity = new GEntityZombieImpl(nmsWorld);
            }
        } else if (GEntityItem.class.isAssignableFrom(classEntity)) {
            entity = new GItemImpl(nmsWorld);
        }

        if (entity == null)
            throw new NullPointerException("Entity creation error");

        entity.setLocation(location);
        return (T) entity;
    }

    @Override
    public GWorldBorder createBorder(World world) {
        return new GWorldBorderImpl(world);
    }

    @Override
    public GEntityPlayer createEntityPlayer(World world, GameProfile gameProfile) {
        DedicatedServer minecraftServer = ((CraftServer)Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        return new GEntityPlayerImpl(minecraftServer, worldServer,
                gameProfile, new PlayerInteractManager(worldServer));
    }

    @Override
    public void updateScaledHealth(Player player) {
        ((CraftPlayer) player).updateScaledHealth();
    }

    @Override
    public void triggerHealthUpdate(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.triggerHealthUpdate();
    }

    @Override
    public void updateAbilities(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.updateAbilities();
    }

    @Override
    public int getEntityID(Entity entity) {
        return ((CraftEntity) entity).getHandle().getId();
    }

    @Override
    public GameProfile getGameProfile(Player player) {
        if (player == null)
            return null;
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        return entityPlayer.getProfile();
    }

    @Override
    public int getItemID(ItemStack itemStack) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        if (nmsItemStack == null)
            return 0;
        return Item.getId(nmsItemStack.getItem());
    }

    @Override
    public int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }

    @Override
    public void sendCrashClientPacket(Player target) {
        if (target == null || !target.isOnline())
            return;

        PacketPlayOutExplosion packet = new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE,
                Double.MAX_VALUE, Float.MAX_VALUE, Collections.EMPTY_LIST, new Vec3D(Double.MAX_VALUE,
                Double.MAX_VALUE, Double.MAX_VALUE));
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public GAnvil getAnvil(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Channel getChannel(Player player) {
        if (player == null || !player.isOnline())
            return null;

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        if (playerConnection == null)
            return null;

        return playerConnection.networkManager.channel;
    }

    @Override
    public GEnchantingTable getEnchantingTable(Player player) {
        return new GEnchantingTableImpl(player);
    }

    @Override
    public void setSkin(Player player, String value, String signature) {
        if (player == null || value == null || signature == null) {
            return;
        }

        CraftPlayer craftPlayer = (CraftPlayer)player;
        GameProfile profile = craftPlayer.getHandle().getProfile();
        Collection<Property> textures = profile.getProperties().get("textures");
        textures.clear();
        textures.add(new Property("textures", value, signature));

        try {
            Method method = CraftPlayer.class.getDeclaredMethod("refreshPlayer");
            method.setAccessible(true);
            method.invoke(craftPlayer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendRespawn(Player player) {

    }

    @Override
    public void sendPacket(Player player, Object packet) {
        if (!(packet instanceof Packet))
            throw new IllegalArgumentException();

        if (player == null || !player.isOnline())
            return;

        EntityPlayer handle = ((CraftPlayer) player).getHandle();
        if (handle == null) {
            return;
        }

        PlayerConnection playerConnection = handle.playerConnection;
        if (playerConnection == null) {
            return;
        }

        playerConnection.sendPacket((Packet<?>) packet);
    }

    @Override
    public void disableFire(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        if (entityPlayer.fireTicks <= 0)
            return;

        entityPlayer.fireTicks = 0;
    }

    @Override
    public PacketContainer getPacketContainer() {
        return container;
    }

    @Override
    public List<Block> getBlocksBelow(Player player) {
        ArrayList<Block> blocksBelow = new ArrayList<>();
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        AxisAlignedBB boundingBox = entityPlayer.getBoundingBox();
        World world = player.getWorld();
        double yBelow = player.getLocation().getY() - 0.0001;
        Block northEast = new Location(world, boundingBox.d(), yBelow, boundingBox.c()).getBlock();
        Block northWest = new Location(world, boundingBox.a(), yBelow, boundingBox.c()).getBlock();
        Block southEast = new Location(world, boundingBox.d(), yBelow, boundingBox.d()).getBlock();
        Block southWest = new Location(world, boundingBox.a(), yBelow, boundingBox.d()).getBlock();
        Block[] blocks = {northEast, northWest, southEast, southWest};
        for (Block block : blocks) {
            if (!blocksBelow.isEmpty()) {
                boolean duplicateExists = false;
                for (Block aBlocksBelow : blocksBelow) {
                    if (aBlocksBelow.equals(block)) {
                        duplicateExists = true;
                    }
                }
                if (!duplicateExists) {
                    blocksBelow.add(block);
                }
            } else {
                blocksBelow.add(block);
            }
        }
        return blocksBelow;
    }

    @Override
    public void playChestAnimation(Block chest, boolean open) {
        Location loc = chest.getLocation();
        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
        worldServer.playBlockAction(new BlockPosition(
                        chest.getX(),
                        chest.getY(),
                        chest.getZ()),
                CraftMagicNumbers.getBlock(chest.getType()), 1, open ? 1 : 0);
    }

    @Override
    public void removeArrowFromPlayer(Player player) {
        if (player == null)
            return;

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        DataWatcher watcher = entityPlayer.getDataWatcher();
        watcher.set(new DataWatcherObject<>(10, DataWatcherRegistry.b), 0);
    }

    @Override
    public void stopReadingPacket(Player player) {
        if (player == null)
            return;

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        if (connection == null)
            return;

        NetworkManager manager = connection.networkManager;
        if (manager == null)
            return;

        sendCrashClientPacket(player);
        manager.stopReading();
    }

    @Override
    public void sendMetaData(Player player, Entity entity) {
        if (entity == null) {
            return;
        }

        net.minecraft.server.v1_16_R3.Entity handle = ((CraftEntity)entity).getHandle();
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entity.getEntityId(), handle.getDataWatcher(), true);
        sendPacket(player, metadata);
    }

    @Override
    public void sendGlowing(Player player, Entity who, boolean glow) {
        if (player == null || who == null) {
            return;
        }

        DataWatcherObject<?> FLAGS = new DataWatcherObject<>(0, DataWatcherRegistry.a);

        net.minecraft.server.v1_16_R3.Entity nmsWho = ((CraftEntity) who).getHandle();
        DataWatcher dataWatcher = nmsWho.getDataWatcher();
        dataWatcher.markDirty(FLAGS);

        sendPacket(player, new PacketPlayOutEntityMetadata(who.getEntityId(), dataWatcher, false));
    }

    @Override
    public void launchInstantFirework(Firework firework) {
        EntityFireworks entityFireworks = ((CraftFirework) firework).getHandle();
        int expectedLifespan = entityFireworks.expectedLifespan;
        try {
            Field ticksFlown = EntityFireworks.class.getDeclaredField("ticksFlown");
            ticksFlown.setAccessible(true);
            ticksFlown.setInt(entityFireworks, expectedLifespan - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
