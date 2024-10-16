package ru.galaxy773.lobby.bossbar;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import ru.galaxy773.lobby.Lobby;

import java.util.Random;

public class BossBarLobby {

    private final Random random = new Random();
    private final int time = 50;
    private double progress;
    @Getter
    private final BossBarReplaced bossBarReplaced;

    public BossBarLobby(Lobby javaPlugin) {
        BossBar bossBar = Bukkit.createBossBar("test", BarColor.GREEN, BarStyle.SEGMENTED_12);
        bossBar.setProgress(0.0);
        this.bossBarReplaced = new BossBarReplaced(bossBar);
        Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, () -> {
            int colorId = this.random.nextInt(BarColor.values().length - 1);
            bossBar.setColor(BarColor.values()[colorId]);
            bossBar.setTitle(this.bossBarReplaced.get());
        }, 0L, 100L);
    }
}

