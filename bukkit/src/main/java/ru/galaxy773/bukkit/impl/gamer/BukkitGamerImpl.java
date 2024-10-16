package ru.galaxy773.bukkit.impl.gamer;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeGroupEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerFriendEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerLoadSectionEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.simpleperms.SimplePermsUtil;
import ru.galaxy773.bukkit.api.utils.version.ViaVersionUtil;
import ru.galaxy773.core.connector.CoreConnector;
import ru.galaxy773.core.connector.bukkit.BukkitConnector;
import ru.galaxy773.core.io.packet.bukkit.BukkitGroupPacket;
import ru.galaxy773.core.io.packet.bukkit.BukkitNetworking;
import ru.galaxy773.core.io.packet.bukkit.BukkitSetting;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;
import ru.galaxy773.multiplatform.api.gamer.customization.JoinMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.PrefixColor;
import ru.galaxy773.multiplatform.api.gamer.customization.QuitMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.friend.Friend;
import ru.galaxy773.multiplatform.api.gamer.friend.FriendAction;
import ru.galaxy773.multiplatform.api.sound.SoundType;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;
import ru.galaxy773.multiplatform.impl.gamer.sections.*;

import java.net.InetAddress;
import java.util.Set;

public class BukkitGamerImpl extends BaseGamer implements BukkitGamer {

    private static final BukkitConnector CONNECTOR = (BukkitConnector) CoreConnector.getInstance();

    @Setter
    private Player player;
    @Getter
    private final InetAddress ip;
    private Version version;
    @Setter
    private ItemStack head;

    public BukkitGamerImpl(AsyncPlayerPreLoginEvent event) {
        super(event.getName());
        this.ip = event.getAddress();
        if (this.getSection(SkinSection.class) != null)
            this.head = HeadUtil.getHead(this.getSkin().getValue());
    }

    @Override
    protected Set<Class<? extends Section>> initSections() {
        AsyncGamerLoadSectionEvent event = new AsyncGamerLoadSectionEvent(this);
        BukkitUtil.callEvent(event);
        return event.getSections();
    }

    @Override
    public String getDisplayName() {
        return this.getChatPrefix() + this.name;
    }

    @Override
    public Player getPlayer() {
        if (this.player == null)
            this.player = Bukkit.getPlayerExact(this.name);

        return this.player;
    }

    @Override
    public Version getVersion() {
        if (this.version == null)
            this.version = ViaVersionUtil.getVersion(this.getPlayer());

        return this.version;
    }

    @Override
    public ItemStack getHead() {
        if (this.head == null)
            this.head = HeadUtil.getEmptyPlayerHead().clone();

        return this.head;
    }

    @Override
    public void playSound(SoundType sound) {
        Player player = this.getPlayer();
        player.playSound(player.getLocation(), Sound.valueOf(sound.getSoundName()), sound.getVolume(), sound.getPitch());
    }

    @Override
    public void playSound(SoundType sound, float pitch, float volume) {
        Player player = this.getPlayer();
        player.playSound(player.getLocation(), Sound.valueOf(sound.getSoundName()), volume, pitch);
    }

    @Override
    public void sendMessage(String message, Object... replace) {
        this.getPlayer().sendMessage(String.format(message, replace));
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Object... replace) {
        this.getPlayer().sendTitle(title == null ? "" : String.format(title, replace), subtitle == null ? "" : String.format(subtitle, replace), fadeIn, stay, fadeOut);
    }

    @Override
    public void sendActionBar(String actionBar, Object... replace) {
        this.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(String.format(actionBar, replace)));
    }

    @Override
    public int getFriendsLimit() {
        return getGroup().getFriendsLimit();
    }

    @Override
    public void changeFriend(int friendID, FriendAction friendAction, boolean saveToMySql) {
        this.getSection(FriendsSection.class).changeFriend(friendID, friendAction, saveToMySql);
        BukkitUtil.callEvent(new GamerFriendEvent(this, new BukkitFriend(friendID), friendAction));
    }

    @Override
    public boolean addExp(int exp) {
        int multiplied = ((int) (exp * this.getGroup().getMultiple())) - exp;
        boolean levelUp = this.getSection(NetworkingSection.class).addExp(exp + multiplied);
        if (multiplied > 0)
            sendMessageLocale("EXP_BOOSTER", multiplied, getGroup().getMultiple(), getGroup().getChatPrefix());

        CONNECTOR.sendPacket(new BukkitNetworking(this.getPlayerID(), BukkitNetworking.Type.EXP, this.getExp()));
        return levelUp;
    }

    @Override
    public void setGold(int gold, boolean saveToMySql) {
        int difference = gold - getGold();
        int multiplied = 0;
        if (difference > 0) {
            multiplied = (int) (difference * this.getGroup().getMultiple()) - difference;
        }
        this.getSection(NetworkingSection.class).setGold(gold + multiplied, saveToMySql);
        if (multiplied > 0) {
            sendMessageLocale("GOLD_BOOSTER", multiplied, getGroup().getMultiple(), getGroup().getChatPrefix());
        }
        CONNECTOR.sendPacket(new BukkitNetworking(this.getPlayerID(), BukkitNetworking.Type.GOLD, this.getGold()));
    }

    @Override
    public void setGroup(Group group, boolean saveToMySql) {
        this.getSection(BaseSection.class).setGroup(group, saveToMySql);
        if (saveToMySql)
            SimplePermsUtil.setGroup(getName(), group.getGroupName());

        BukkitUtil.callEvent(new GamerChangeGroupEvent(this, group));
        CONNECTOR.sendPacket(new BukkitGroupPacket(this.getPlayerID(), group.getLevel()));
    }

    @Override
    public void setSetting(SettingsType settingsType, boolean flag, boolean saveToMySql) {
        this.getSection(SettingsSection.class).setSetting(settingsType, flag, saveToMySql);
        CONNECTOR.sendPacket(new BukkitSetting(this.getPlayerID(), settingsType, flag));
    }

    @Override
    public void setCoins(int coins, boolean saveToMySql) {
        this.getSection(NetworkingSection.class).setCoins(coins, saveToMySql);
        CONNECTOR.sendPacket(new BukkitNetworking(this.getPlayerID(), BukkitNetworking.Type.COINS, coins));
    }

    @Override
    public void setKeys(KeysType keyType, int keys, boolean saveToMySql) {
        this.getSection(KeysSection.class).setKeys(keyType, keys, saveToMySql);
        CONNECTOR.sendPacket(new BukkitNetworking(this.getPlayerID(), BukkitNetworking.Type.KEYS, keyType.getId(), keys));
    }

    @Override
    public int getPlayTime() {
        return this.getSection(NetworkingSection.class).getPlayTime() + getPlayTimeOnJoin();
    }

    @Override
    public TIntObjectMap<Friend> getFriends() {
        FriendsSection friendsSection = this.getSection(FriendsSection.class);
        TIntObjectMap<Friend> friends = new TIntObjectHashMap<>();
        TIntSet friendsIds = friendsSection.getFriends();
        for (int playerID : friendsIds.toArray(new int[friendsIds.size()])) {
            friends.put(playerID, new BukkitFriend(playerID));
        }
        return friends;
    }

    @Override
    public TIntSet getFriendsIDs() {
        return this.getSection(FriendsSection.class).getFriends();
    }

    @Override
    public int getPlayTimeOnJoin() {
        return (int) ((System.currentTimeMillis() - this.getStartTime()) / 1000);
    }

    @Override
    public String getTagPrefix() {
        Group group = this.getGroup();
        return group == Group.CHRONOS ? getPrefixColor().getColorCode() + "\u00A7l" + ChatColor.stripColor(group.getTagPrefix()) + "\u00A7f" : this.getGroup().getTagPrefix();
    }

    @Override
    public String getChatPrefix() {
        Group group = this.getGroup();
        return group == Group.CHRONOS ? getPrefixColor().getColorCode() + "\u00A7l" + ChatColor.stripColor(group.getChatPrefix()) + "\u00A7f" : this.getGroup().getChatPrefix();
    }

    @Override
    public Set<TitulType> getTituls() {
        return this.getSection(CustomizationSection.class).getTitulTypes();
    }

    @Override
    public void addTitul(TitulType titulType) {
        this.getSection(CustomizationSection.class).addTitul(titulType);
    }

    @Override
    public TitulType getSelectedTitul() {
        return this.getSection(CustomizationSection.class).getSelectedTitulType();
    }

    @Override
    public void setSelectedTitul(TitulType selectedTitulType) {
        this.getSection(CustomizationSection.class).setSelectedTitulType(selectedTitulType);
    }

    @Override
    public JoinMessage getJoinMessage() {
        return this.getSection(CustomizationSection.class).getJoinMessage();
    }

    @Override
    public void setJoinMessage(JoinMessage joinMessage) {
        this.getSection(CustomizationSection.class).setJoinMessage(joinMessage);
    }

    @Override
    public QuitMessage getQuitMessage() {
        return this.getSection(CustomizationSection.class).getQuitMessage();
    }

    @Override
    public void setQuitMessage(QuitMessage quitMessage) {
        this.getSection(CustomizationSection.class).setQuitMessage(quitMessage);
    }

    @Override
    public PrefixColor getPrefixColor() {
        return this.getSection(CustomizationSection.class).getPrefixColor();
    }

    @Override
    public void setPrefixColor(PrefixColor prefixColor) {
        this.getSection(CustomizationSection.class).setPrefixColor(prefixColor);
    }
}
