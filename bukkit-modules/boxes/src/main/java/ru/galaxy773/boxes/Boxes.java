package ru.galaxy773.boxes;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.boxes.api.ItemManager;
import ru.galaxy773.boxes.data.Box;
import ru.galaxy773.boxes.gui.BoxGui;
import ru.galaxy773.boxes.manager.CraftItemManager;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.bukkit.api.utils.listener.FastEvent;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Boxes extends JavaPlugin {

    @Getter
    private static Boxes instance;

    private ItemManager itemManager;
    private List<Box> boxes;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        this.itemManager = new CraftItemManager();
        this.boxes = new ArrayList<>();
        FileConfiguration config = BukkitAPI.getConfigAPI().loadConfig(this, "boxes.yml");
        config.getStringList("locations")
                .forEach(location -> boxes.add(
                        new Box(LocationUtil.stringToLocation(location.split(",")[0], false),
                                location.split(",")[1].equalsIgnoreCase("x"))));
        this.registerInteract();
    }

    public void onDisable() {
        Box.getThreads().forEach(future -> future.cancel(true));
    }

    private void registerInteract() {
        FastListener.create().event(FastEvent.builder(PlayerInteractEvent.class).priority(EventPriority.LOWEST).ignoreCancelled(true).filter(event -> event.getAction() == Action.RIGHT_CLICK_BLOCK).handler(event -> {
            Box box = this.boxes.stream().filter(b -> b.getBlock().equals(event.getClickedBlock())).findFirst().orElse(null);
            if (box != null) {
                event.setCancelled(true);
                if (box.isOpen()) {
                    BukkitGamer gamer = GamerManager.getGamer(event.getPlayer());
                    gamer.sendMessageLocale("BOXES_IS_OPEN");
                    return;
                }
                new BoxGui(event.getPlayer(), box).open();
            }
        }).build()).register(this);
    }
}

