package ru.galaxy773.lobby.bossbar;

import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerQuitEvent;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;

public class BossBarListener extends GListener<Lobby> {

    private final BossBar bossBar;

    public BossBarListener(Lobby javaPlugin) {
        super(javaPlugin);
        this.bossBar = new BossBarLobby(javaPlugin).getBossBarReplaced().getBossBar();
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onAsyncJoin(AsyncGamerJoinEvent event) {
        if (!event.getGamer().getSetting(SettingsType.BOSSBAR))
            return;

        this.bossBar.addPlayer(event.getGamer().getPlayer());
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onQuit(AsyncGamerQuitEvent event) {
        if (!event.getGamer().getSetting(SettingsType.BOSSBAR)) {
            return;
        }
        this.bossBar.removePlayer(event.getGamer().getPlayer());
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    private void onBossbar(GamerChangeSettingEvent event) {
        if (event.getSetting() != SettingsType.BOSSBAR) {
            return;
        }
        BukkitUtil.runTaskAsync(() -> {
            if (event.isEnable()) {
                this.bossBar.addPlayer(event.getGamer().getPlayer());
            } else {
                this.bossBar.removePlayer(event.getGamer().getPlayer());
            }
        });
    }
}

