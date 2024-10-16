package ru.galaxy773.boxes.data;

import com.google.common.collect.ImmutableList;
import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import ru.galaxy773.boxes.Boxes;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.boxes.api.ItemManager;
import ru.galaxy773.boxes.utils.BoxUtil;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.entity.depend.PacketObject;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.api.utils.bukkit.SoundUtil;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.multiplatform.api.boxes.AnimationType;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.sound.SoundType;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Box {

    private static final Boxes JAVA_PLUGIN = Boxes.getInstance();
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final List<Future<?>> THREADS = new ArrayList<>();
    private static final HologramAPI HOLOGRAM_API = BukkitAPI.getHologramAPI();
    private static final ParticleAPI PARTICLE_API = BukkitAPI.getParticleAPI();
    private static final NmsManager NMS_MANAGER = NmsAPI.getManager();
    private static final ItemManager ITEM_MANAGER = Boxes.getInstance().getItemManager();
    private static final Random RANDOM = new Random();
    private static final ImmutableList<ItemStack> HEADS = ImmutableList.of(
            HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNlZjlhYTE0ZTg4NDc3M2VhYzEzNGE0ZWU4OTcyMDYzZjQ2NmRlNjc4MzYzY2Y3YjFhMjFhODViNyJ9fX0="),
            HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA4Y2U3ZGViYTU2YjcyNmE4MzJiNjExMTVjYTE2MzM2MTM1OWMzMDQzNGY3ZDVlM2MzZmFhNmZlNDA1MiJ9fX0="),
            HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNlMTY1ODU3YzRjMmI3ZDZkOGQ3MGMxMDhjYzZkNDY0ZjdmMzBlMDRkOTE0NzMxYjU5ZTMxZDQyNWEyMSJ9fX0="));
    private static final Color[] COLORS = new Color[] {
            Color.RED, Color.YELLOW, Color.LIME, Color.AQUA,
    };

    @Getter
    private final Block block;
    @Getter
    private final Location location;
    @Getter
    private final Hologram hologram;
    @Getter
    private boolean open;
    @Getter
    private final boolean rotateByX;
    @Getter
    private final Set<Player> playersOpenGui = new ConcurrentSet<>();
    private Future<?> future = null;

    public Box(Location location, boolean rotateByX) {
        this.block = location.getBlock();
        this.location = location.clone().add(0.5, 0.5, 0.5);
        this.rotateByX = rotateByX;
        this.hologram = HOLOGRAM_API.createHologram(JAVA_PLUGIN, location.clone().add(0.0, 1.0, 0.0).subtract(-0.5, 0.0, -0.5));
        Lang.getList("BOXES_HOLOGRAM").forEach(line -> {
            if (line.startsWith("{item:")) {
                this.hologram.addDropLine(false, ItemBuilder.builder(line.split(":")[1].split("}")[0]).build());
            } else if (line.startsWith("{bigitem:")) {
                this.hologram.addBigItemLine(false, ItemBuilder.builder(line.split(":")[1].split("}")[0]).build());
            } else {
                this.hologram.addTextLine(line);
            }
        });
        this.hologram.setPublic(true);
    }

    public void open(BukkitGamer gamer, KeysType keysType) {
        if (ITEM_MANAGER.getRewards(keysType) == null) {
            throw new NullPointerException("Box " + keysType + " not found in item manager");
        }
        this.hologram.setPublic(false);
        this.open = true;
        this.playersOpenGui.forEach(pl -> {
            if (pl.isOnline()) {
                pl.closeInventory();
            }
        });
        List<BoxReward> rewards = ITEM_MANAGER.getRewards(keysType);
        Collections.shuffle(rewards);
        BoxReward winReward = BoxUtil.getWinItem(rewards);
        if (winReward == null) {
            throw new RuntimeException("Win reward is null in box " + keysType.name());
        }
        if (keysType.getAnimationType() == AnimationType.CIRCLE) {
            this.startCircleAnimation(gamer, rewards, winReward);
        } else {
            this.startConusAnimation(gamer, rewards, winReward);
        }
    }

    public void startCircleAnimation(BukkitGamer gamer, List<BoxReward> items, BoxReward winReward) {
        this.future = EXECUTOR_SERVICE.submit(() -> {
            try {
                RotatingState state = RotatingState.FROST_LORD;

                Location frostLord = location.clone().subtract(0.0, 1.0, 0.0);
                double decrease = 0;
                while (state == RotatingState.FROST_LORD) {
                    Thread.sleep(50);
                    decrease += Math.PI / 16;
                    //4 линии вращения в круге
                    for (double pointOffset = 0; pointOffset <= Math.PI * 2; pointOffset += Math.PI / 2) {
                        double x = 0.2 * (Math.PI * 4 - decrease) * Math.cos(decrease + pointOffset);
                        double y = 0.2 * decrease;
                        double z = 0.2 * (Math.PI * 4 - decrease) * Math.sin(decrease + pointOffset);
                        frostLord.add(x, y, z);
                        PARTICLE_API.sendEffect(ParticleEffect.FIREWORKS_SPARK, frostLord);
                        frostLord.subtract(x, y, z);

                        if (decrease >= Math.PI * 4) {
                            NMS_MANAGER.playChestAnimation(block, true);
                            state = RotatingState.ROTATING;
                            break;
                        }
                    }
                }

                //тики с начала
                int tick = 0;
                //тики с момента, когда state становится RotatingState.CANCEL
                int cancelTick = 0;
                double particlesDegree = 0;
                //90 градусов - верхняя точка круга
                double hologramDegree = 90;
                //скорость (5 * deceleration - угол поворота в градусах)
                double deceleration = 1.5;
                Location heart = location.clone().add(0, 1.75, 0);
                Location particles = location.clone();
                List<Hologram> holograms = new ArrayList<>();
                //для расчета выигрышной локации по формуле
                double endDegreeOffset = -1;
                while (state != RotatingState.END) {
                    Thread.sleep(50);

                    if (endDegreeOffset != -1 && state == RotatingState.ROTATING)
                        if (hologramDegree >= endDegreeOffset) {
                            state = RotatingState.CANCEL;

                            SoundUtil.playInRadius(this.location, SoundType.LEVEL_UP, 10);

                            BukkitUtil.runTask(() -> {
                                winReward.onReward(gamer);
                                winReward.onMessage(gamer);
                            });
                        } else {
                            //чтобы встало ровно на середину - по факту не помогает,
                            //поскольку endDegreeOffset должен ровно делиться на 360
                            if (hologramDegree + 5 * Math.max(deceleration, 0.25) > endDegreeOffset)
                                hologramDegree = endDegreeOffset;
                        }

                    if (tick % 5 == 0) {
                        PARTICLE_API.sendEffect(ParticleEffect.HEART, heart);

                        if (state == RotatingState.ROTATING)
                            SoundUtil.playInRadius(location, SoundType.CLICK, 10);
                    }

                    if (particlesDegree >= 360)
                        particlesDegree = 0;

                    int colorIndex = 0;
                    for (double offset = 0; offset < 360; offset += 45) {
                        if (colorIndex == 4)
                            colorIndex = 0;

                        Color color = COLORS[colorIndex];
                        double x = 1.5 * (Math.cos(Math.toRadians(particlesDegree + offset)));
                        double y = 1.5 * (Math.sin(Math.toRadians(particlesDegree + offset)));
                        particles.add(rotateByX ? x : 0, y, rotateByX ? 0 : x);
                        PARTICLE_API.sendEffect(ParticleEffect.REDSTONE, color.getRed(),
                                color.getGreen(), color.getBlue(), particles, 30.0);
                        particles.subtract(rotateByX ? x : 0, y, rotateByX ? 0 : x);
                        colorIndex++;
                    }
                    particlesDegree += 5;

                    if (holograms.size() < items.size() && hologramDegree >= (double) (360 / items.size() * holograms.size() + 90)) {
                        double degree = Math.toRadians(hologramDegree);
                        double x = 1.5 * Math.cos(degree);
                        double y = 1.5 * Math.sin(degree);
                        Location hologramLocation = location.clone().add(rotateByX ? x : 0, y - 0.25, rotateByX ? 0 : x);
                        BoxReward item = items.get(holograms.size());
                        Hologram hologram = HOLOGRAM_API.createHologram(JAVA_PLUGIN, hologramLocation);
                        hologram.addTextLine(item.getTitle());
                        hologram.addDropLine(false, item.getIcon());
                        hologram.setPublic(true);
                        holograms.add(hologram);

                        if (item == winReward)
                            endDegreeOffset = 360 * 5 - ((double) (360 / items.size() * (holograms.size() + 1))) + 180;

                        continue;
                    }

                    if (state == RotatingState.ROTATING) {
                        double offset = 0.0;
                        for (Hologram hologram : holograms) {
                            double degree = Math.toRadians((hologramDegree % 360) + offset);
                            double x = 1.5 * Math.cos(degree);
                            double y = 1.5 * Math.sin(degree);
                            Location hologramLocation = location.clone().add(rotateByX ? x : 0, y - 0.25, rotateByX ? 0 : x);
                            hologram.onTeleport(hologramLocation);
                            offset += (double) 360 / items.size();
                        }

                        hologramDegree += 5 * Math.max(deceleration, 0.25);
                        deceleration -= tick > 60 ? 0.00375 : tick > 40 ? 0.003 : tick > 20 ? 0.002 : 0.00125;
                    } else {
                        cancelTick++;
                    }

                    if (cancelTick == 60)
                        state = RotatingState.END;
                    else
                        tick++;
                }

                holograms.forEach(Hologram::remove);
                holograms.clear();
                NMS_MANAGER.playChestAnimation(this.block, false);
                this.hologram.setPublic(true);
                THREADS.remove(this.future);
                this.open = false;
            } catch (Exception ex) {
                NMS_MANAGER.playChestAnimation(this.block, false);
                BukkitUtil.runTask(() -> {
                    winReward.onReward(gamer);
                    winReward.onMessage(gamer);
                });
            }
        });

        THREADS.add(this.future);
    }

    public void startConusAnimation(BukkitGamer gamer, List<BoxReward> items, BoxReward winReward) {
        this.future = EXECUTOR_SERVICE.submit(() -> {
            try {
                Thread.sleep(200L);
                NMS_MANAGER.playChestAnimation(this.block, true);
                Thread.sleep(800L);
                Location location = this.location.clone();
                Hologram chestHologram = HOLOGRAM_API.createHologram(JAVA_PLUGIN, location);
                chestHologram.addBigItemLine(false, HEADS.get(RANDOM.nextInt(HEADS.size())));
                chestHologram.setPublic(true);
                Thread.sleep(200L);
                double startR = 2.0;
                double radiusRate = 0.007;
                double yRate = 0.005;
                int firstIterations = 20;
                int nowPosition = 50;
                List<Location> circlePositions = BoxUtil.getCircleSide(location, startR);
                Vector dist = circlePositions.get(nowPosition).subtract(chestHologram.getLocation()).toVector();
                for (int i = 1; i <= firstIterations; ++i) {
                    Location current = location.clone().add(dist.clone().multiply((double)i / (double)firstIterations));
                    current.setY(location.getY());
                    chestHologram.onTeleport(current);
                    Thread.sleep(15L);
                }
                double nowY = location.getY();
                while (startR >= 0.05) {
                    circlePositions = BoxUtil.getCircleSide(location, startR);
                    Location now = circlePositions.get(nowPosition++ % 100);
                    now.setY(nowY);
                    chestHologram.onTeleport(now);
                    PARTICLE_API.sendEffect(ParticleEffect.FIREWORKS_SPARK, chestHologram.getLocation().clone().add(0.0, 0.5, 0.0), 0.01f, 1);
                    nowY += yRate;
                    startR -= radiusRate;
                    Thread.sleep(7L);
                }
                Location center = location.clone();
                center.setY(nowY);
                chestHologram.onTeleport(center);
                Thread.sleep(600L);
                Location winHoloLocation = chestHologram.getLocation();
                chestHologram.remove();
                PARTICLE_API.sendEffect(ParticleEffect.EXPLOSION_NORMAL, winHoloLocation.clone().add(0.0, 0.4, 0.0), 0.05f, 25);
                int timesScroll = RANDOM.nextInt(6) + 3;
                for (int i = 0; i < timesScroll; ++i) {
                    location.getWorld().playSound(location, Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
                    Vector to = winHoloLocation.clone().subtract(location).toVector();
                    ArrayList<Hologram> itemHologramList = new ArrayList<>();
                    Hologram itemHolo = HOLOGRAM_API.createHologram(JAVA_PLUGIN, location.clone().add(0.0, 0.7, 0.0));
                    itemHolo.addTextLine(items.get(i % items.size()).getTitle());
                    itemHolo.addDropLine(false, items.get(i % items.size()).getIcon());
                    itemHolo.setPublic(true);
                    itemHologramList.add(itemHolo);
                    for (int j = 0; j < 10; ++j) {
                        Location spawnLocation = location.clone().add(0.0, 0.2, 0.0).clone().add(to.clone().multiply(((double)j + 1.0) / 10.0));
                        itemHologramList.forEach(hologram -> hologram.onTeleport(spawnLocation));
                        Thread.sleep(25L);
                    }
                    Thread.sleep(250L);
                    itemHologramList.forEach(PacketObject::remove);
                }
                ArrayList<Hologram> winHolo = new ArrayList<>();
                Hologram holo = HOLOGRAM_API.createHologram(JAVA_PLUGIN, winHoloLocation.clone().add(0.0, 0.0, 0.0));
                holo.addTextLine(winReward.getTitle());
                holo.addDropLine(false, winReward.getIcon());
                holo.setPublic(true);
                winHolo.add(holo);
                PARTICLE_API.sendEffect(ParticleEffect.FLAME, this.getLocation().add(0.0, 1.2, 0.0), 0.0f, 0.0f, 0.0f, 0.05f, 30, 128.0);
                SoundUtil.playInRadius(this.location, SoundType.LEVEL_UP, 10);

                BukkitUtil.runTask(() -> {
                    winReward.onReward(gamer);
                    winReward.onMessage(gamer);
                });

                Thread.sleep(2000L);
                winHolo.forEach(holoTeleport -> holoTeleport.onTeleport(location));
                Thread.sleep(250L);
                winHolo.forEach(PacketObject::remove);
                NMS_MANAGER.playChestAnimation(this.block, false);
                Thread.sleep(600L);
                this.open = false;
                this.hologram.setPublic(true);
                THREADS.remove(this.future);
            }
            catch (InterruptedException ignored) {
                NMS_MANAGER.playChestAnimation(this.block, false);
                BukkitUtil.runTask(() -> {
                    winReward.onReward(gamer);
                    winReward.onMessage(gamer);
                });
            }
        });
        THREADS.add(this.future);
    }

    public Location getLocation() {
        return this.location.clone();
    }

    public static List<Future<?>> getThreads() {
        return THREADS;
    }

    private enum RotatingState {
        FROST_LORD,
        ROTATING,
        CANCEL,
        END
    }
}

