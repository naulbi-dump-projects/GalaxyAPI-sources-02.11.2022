package ru.galaxy773.bukkit.impl.gamer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.galaxy773.multiplatform.api.gamer.GamerAPI;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.friend.Friend;

@Getter
@RequiredArgsConstructor
public class BukkitFriend implements Friend {

    private final int playerID;

    @Override
    public boolean isOnline() {
        return getGamer().isOnline();
    }

    @Override
    public <T extends IBaseGamer> T getGamer() {
        return (T) GamerAPI.getFromCacheOrLoad(playerID);
    }
}
