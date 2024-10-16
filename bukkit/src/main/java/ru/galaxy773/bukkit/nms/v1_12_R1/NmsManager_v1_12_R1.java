package ru.galaxy773.bukkit.nms.v1_12_R1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.netty.channel.Channel;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.nms.interfaces.GWorldBorder;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.*;
import ru.galaxy773.bukkit.nms.interfaces.gui.GAnvil;
import ru.galaxy773.bukkit.nms.interfaces.gui.GEnchantingTable;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.*;
import ru.galaxy773.bukkit.nms.v1_12_R1.gui.GAnvilImpl;
import ru.galaxy773.bukkit.nms.v1_12_R1.gui.GEnchantingTableImpl;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.PacketContainerImpl;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("all")
public class NmsManager_v1_12_R1 implements NmsManager {

    private final PacketContainer container = new PacketContainerImpl();

    @Override
    public <T extends GEntity> T createGEntity(Class<T> classEntity, Location location) {
        if (classEntity == GEntity.class || classEntity == GEntityLiving.class || classEntity == GEntityPlayer.class)
            throw new IllegalArgumentException("Type for entity not found");
        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
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
    public List<Block> getBlocksBelow(Player player) {
        return null;
    }

    @Override
    public GWorldBorder createBorder(World world) {
        return new GWorldBorderImpl(world);
    }
    
    @Override
    public GEntityPlayer createEntityPlayer(World world, GameProfile gameProfile) {
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        return new GEntityPlayerImpl(minecraftServer, worldServer, gameProfile,
                new PlayerInteractManager(worldServer));
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
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        if (nmsItemStack == null)
            return 0;
        return Item.getId(nmsItemStack.getItem());
    }

    @Override
    public int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }
    
    @Override
    public GAnvil getAnvil(Player player) {
        return new GAnvilImpl(((CraftPlayer) player).getHandle());
    }

    @Override
    public GEnchantingTable getEnchantingTable(Player player) {
        return new GEnchantingTableImpl(player);
    }

    @Override
    public void setSkin(Player player, String value, String signature) {
        if (value == null || signature == null)
            return;

        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer nmsPlayer = craftPlayer.getHandle();

        if (player.isSleeping()) {
            PacketPlayInEntityAction action = new PacketPlayInEntityAction();

            ReflectionUtil.setFieldValue(action, "a", nmsPlayer.getId());
            ReflectionUtil.setFieldValue(action, "animation", PacketPlayInEntityAction.EnumPlayerAction.STOP_SLEEPING);

            nmsPlayer.playerConnection.networkManager.channel.pipeline().fireChannelRead(action);
        }

        GameProfile profile = nmsPlayer.getProfile();
        profile.getProperties().get("textures").clear();
        profile.getProperties().get("textures").add(new Property("textures", value, signature));

        org.bukkit.inventory.PlayerInventory inventory = player.getInventory();

        PacketPlayOutPlayerInfo removeTab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo
                .EnumPlayerInfoAction.REMOVE_PLAYER, nmsPlayer);
        PacketPlayOutPlayerInfo addTab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo
                .EnumPlayerInfoAction.ADD_PLAYER, nmsPlayer);

        int dimensionId = player.getWorld().getEnvironment().getId();
        WorldServer worldServer = (WorldServer) nmsPlayer.getWorld();
        EnumGamemode enumGamemode = EnumGamemode.valueOf(player.getGameMode().toString());
        PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(dimensionId, worldServer.getDifficulty(),
                worldServer.worldData.getType(), enumGamemode);

        Location loc = player.getLocation();
        PacketPlayOutPosition pos = new PacketPlayOutPosition(loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch(),
                new HashSet<>(), -1337);

        PlayerConnection playerConnection = craftPlayer.getHandle().playerConnection;
        playerConnection.sendPacket(removeTab);
        playerConnection.sendPacket(addTab);
        playerConnection.sendPacket(respawn);
        playerConnection.sendPacket(pos);

        player.setExp(player.getExp());
        player.setWalkSpeed(player.getWalkSpeed());
        player.updateInventory();
        inventory.setHeldItemSlot(inventory.getHeldItemSlot());
        craftPlayer.updateScaledHealth();

        Bukkit.getOnlinePlayers().stream()
                .filter(onlinePlayer -> onlinePlayer.canSee(player))
                .forEach((other) -> {
                    other.hidePlayer(player);
                    other.showPlayer(player);
                });
    }

    @Override
    public void sendRespawn(Player player) {
    	if(player == null || !player.isOnline()) {
    		return;
    	}
    	PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
    	sendPacket(player, packet);
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
    public Channel getChannel(Player player) {
        if (player == null || !player.isOnline())
            return null;

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        if (playerConnection == null)
            return null;

        return playerConnection.networkManager.channel;
    }
    
    @Override
    public void sendPacket(Player player, Object packet) {
        if (!(packet instanceof Packet))
            throw new IllegalArgumentException("Class not packet");

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
    public void playChestAnimation(Block chest, boolean open) {
        Location loc = chest.getLocation();
        WorldServer worldServer = ((CraftWorld) loc.getWorld()).getHandle();
        worldServer.playBlockAction(new BlockPosition(
                        chest.getX(),
                        chest.getY(),
                        chest.getZ()),
                CraftMagicNumbers.getBlock(chest), 1, open ? 1 : 0);
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
        if (entity == null)
            return;

        net.minecraft.server.v1_12_R1.Entity handle = ((CraftEntity) entity).getHandle();
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entity.getEntityId(), handle.getDataWatcher(), true);
        sendPacket(player, metadata);
    }

    @Override
    public void sendGlowing(Player player, Entity who, boolean glow) {
        if (player == null || who == null) {
            return;
        }

        net.minecraft.server.v1_12_R1.Entity nmsEntity = ((CraftEntity) who).getHandle();

        DataWatcher toCloneDataWatcher = nmsEntity.getDataWatcher();
        DataWatcher newDataWatcher = new DataWatcher(nmsEntity);

        Map<Integer, DataWatcher.Item<?>> currentMap = ReflectionUtil.getFieldValue(toCloneDataWatcher, "d");
        Map<Integer, DataWatcher.Item<?>> newMap = new HashMap<>();

        for (Integer integer : currentMap.keySet()) {
            newMap.put(integer, currentMap.get(integer).d());
        }

        DataWatcher.Item item = newMap.get(0);
        byte initialBitMask = (Byte) item.b();
        byte bitMaskIndex = (byte) 6;
        item.a((byte) (glow ? (initialBitMask | 1 << bitMaskIndex) : (initialBitMask & ~(1 << bitMaskIndex))));

        ReflectionUtil.setFieldValue(newDataWatcher, "d", newMap);

        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(who.getEntityId(),
                newDataWatcher, true);

        sendPacket(player, metadataPacket);
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
