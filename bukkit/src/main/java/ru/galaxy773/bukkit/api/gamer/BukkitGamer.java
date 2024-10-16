package ru.galaxy773.bukkit.api.gamer;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.set.TIntSet;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.OnlineGamer;
import ru.galaxy773.multiplatform.api.gamer.customization.JoinMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.PrefixColor;
import ru.galaxy773.multiplatform.api.gamer.customization.QuitMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.friend.Friend;
import ru.galaxy773.multiplatform.api.gamer.friend.FriendAction;
import ru.galaxy773.multiplatform.api.sound.SoundType;

import java.util.Set;

public interface BukkitGamer extends OnlineGamer, IBaseGamer {

    Player getPlayer();

    ItemStack getHead();

    void setHead(ItemStack head);

    void playSound(SoundType sound);

    void playSound(SoundType sound, float pitch, float volume);

    TIntObjectMap<Friend> getFriends();

    TIntSet getFriendsIDs();

    int getFriendsLimit();

    void changeFriend(int friendId, FriendAction friendAction, boolean saveToMySql);

    default void changeFriend(Friend friend, FriendAction friendAction, boolean saveToMySql) {
        changeFriend(friend.getPlayerID(), friendAction, saveToMySql);
    }

    default boolean isFriend(String name) {
        return getFriends().valueCollection().stream().anyMatch(friend -> friend.getGamer().getName().equals(name));
    }

    default boolean isFriend(Friend friend) {
        return isFriend(friend.getPlayerID());
    }

    default boolean isFriend(int playerID) {
        return getFriends().containsKey(playerID);
    }

    String getTagPrefix();

    String getChatPrefix();

    Set<TitulType> getTituls();

    void addTitul(TitulType titulType);

    TitulType getSelectedTitul();

    void setSelectedTitul(TitulType selectedTitulType);

    JoinMessage getJoinMessage();

    void setJoinMessage(JoinMessage joinMessage);

    QuitMessage getQuitMessage();

    void setQuitMessage(QuitMessage quitMessage);

    PrefixColor getPrefixColor();

    void setPrefixColor(PrefixColor prefixColor);
}
