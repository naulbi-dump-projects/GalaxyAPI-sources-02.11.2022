package ru.galaxy773.bukkit.impl.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.bukkit.impl.gui.types.GuiType;
import ru.galaxy773.bukkit.impl.gui.types.UpdatableGui;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GuiThread {

    public GuiThread(GuiManager<AbstractGui<?>> guiManager) {
        Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
                .setNameFormat("GuiThread #%s")
                .build()
        ).scheduleAtFixedRate(() -> {
            for (Map<String, AbstractGui<?>> playerGuis : guiManager.getPlayerGuis().values()) {
                for (AbstractGui<?> gui : playerGuis.values()) {
                    if (gui.getGuiType() != GuiType.DYNAMIC) {
                        continue;
                    }
                    UpdatableGui<?> updatableGui = (UpdatableGui<?>) gui;
                    if (updatableGui.isOpened()) {
                        updatableGui.update();
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
