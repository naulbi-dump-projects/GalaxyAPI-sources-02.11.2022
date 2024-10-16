package ru.galaxy773.bukkit.impl.gui.manager;

import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.api.utils.listener.FastEvent;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;
import ru.galaxy773.bukkit.guis.profile.InformationGui;
import ru.galaxy773.bukkit.guis.profile.ProfileMainPage;
import ru.galaxy773.bukkit.guis.profile.RewardGui;
import ru.galaxy773.bukkit.guis.profile.SettingsGui;
import ru.galaxy773.bukkit.guis.profile.donatemenu.DonateMenuGui;
import ru.galaxy773.bukkit.guis.profile.tituls.TitulsCategoryGui;
import ru.galaxy773.bukkit.impl.gui.GuiThread;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CraftGuiManager implements GuiManager<AbstractGui<?>> {

    @Getter
    private final Map<String, Map<String, AbstractGui<?>>> playerGuis = new ConcurrentHashMap<>();
    private final Set<String> guis = new ConcurrentSet<>();

    public CraftGuiManager(BukkitPlugin javaPlugin) {
        FastListener.create()
                .event(FastEvent.builder(PlayerQuitEvent.class)
                        .priority(EventPriority.LOWEST)
                        .handler(event -> removeALL(event.getPlayer()))
                        .build())
                .register(javaPlugin);
        createGui(ProfileMainPage.class);
        createGui(SettingsGui.class);
        createGui(TitulsCategoryGui.class);
        createGui(DonateMenuGui.class);
        createGui(RewardGui.class);
        createGui(InformationGui.class);

        new GuiThread(this);
    }

    @Override
    public void createGui(Class<? extends AbstractGui<?>> clazz) {
        String name = clazz.getSimpleName();
        if (guis.contains(name)) {
            return;
        }

        guis.add(name);
    }

    @Override
    public void removeGui(Class<? extends AbstractGui<?>> clazz) {
        String nameClazz = clazz.getSimpleName();
        for (String name : playerGuis.keySet()) {
            Map<String, AbstractGui<?>> guis = playerGuis.get(name);
            for (String guiName : guis.keySet()) {
                if (guiName.equals(nameClazz)) {
                    guis.remove(guiName);
                }
            }
        }
    }

    @Override
    public <T extends AbstractGui<?>> T getGui(Class<T> clazz, Player player) {
        String guiName = clazz.getSimpleName();

        if (!guis.contains(guiName)) {
            return null;
        }

        String name = player.getName();

        Map<String, AbstractGui<?>> guis = playerGuis.get(name);
        if (guis == null) {
            guis = new ConcurrentHashMap<>();
            playerGuis.put(name, guis);
        }

        T gui = (T) guis.get(guiName);

        if (gui == null) {
            try {
                gui = clazz.getConstructor(Player.class).newInstance(player);
                guis.put(guiName, gui);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return gui;
    }

    @Override
    public void removeALL(Player player) {
        playerGuis.remove(player.getName());
    }
}