package ru.galaxy773.lobby.customitems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeGroupEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSkinEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;

public class CustomItemListener extends GListener<Lobby> {

    public CustomItemListener(Lobby plugin) {
        super(plugin);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onAsyncJoin(AsyncGamerJoinEvent event) {
        Player player = event.getGamer().getPlayer();
        CustomItem.giveProfileItem(player);
        CustomItem.giveItems(player);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onChangeGroup(GamerChangeGroupEvent event) {
        CustomItem.giveProfileItem(event.getGamer().getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChangeSkin(GamerChangeSkinEvent event) {
        CustomItem.giveProfileItem(event.getGamer().getPlayer());
    }
}

