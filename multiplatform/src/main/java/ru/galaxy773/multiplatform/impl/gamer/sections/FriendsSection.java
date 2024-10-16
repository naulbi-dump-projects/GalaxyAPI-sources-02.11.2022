package ru.galaxy773.multiplatform.impl.gamer.sections;

import gnu.trove.set.TIntSet;
import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.friend.Friend;
import ru.galaxy773.multiplatform.api.gamer.friend.FriendAction;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

public class FriendsSection extends Section {

    @Getter
    private TIntSet friends;

    public FriendsSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        this.friends = PlayerInfoLoader.getFriends(super.baseGamer.getPlayerID());
        return true;
    }

    public void changeFriend(int friendID, FriendAction friendAction, boolean saveToMySql) {
        if (friendAction == FriendAction.ADD) {
            if (friends.contains(friendID)) {
                return;
            }
            friends.add(friendID);
        } else {
            friends.remove(friendID);
        }
        if (saveToMySql)
            PlayerInfoLoader.changeFriend(super.baseGamer.getPlayerID(), friendID, friendAction);
    }

    public int getFriendsLimit() {
        return super.baseGamer.getGroup().getFriendsLimit();
    }
}
