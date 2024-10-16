package ru.galaxy773.bukkit.api.event.gamer;

import lombok.Getter;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.gamer.friend.Friend;
import ru.galaxy773.multiplatform.api.gamer.friend.FriendAction;

@Getter
public class GamerFriendEvent extends GamerEvent {

    private final Friend friend;
    private final FriendAction friendAction;

    public GamerFriendEvent(BukkitGamer gamer, Friend friend, FriendAction friendAction) {
        super(gamer);
        this.friend = friend;
        this.friendAction = friendAction;
    }
}
