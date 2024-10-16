package ru.galaxy773.bukkit.impl.listeners.networking;


import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.messaging.PluginMessageListener;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeGroupEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.api.utils.skin.SkinUtil;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.skin.SkinType;

public final class BungeeMessageListener extends GListener<BukkitPlugin> implements PluginMessageListener {

    private final BukkitPlugin javaPlugin;

    public BungeeMessageListener(BukkitPlugin javaPlugin) {
        super(javaPlugin);
        this.javaPlugin = javaPlugin;
        Bukkit.getMessenger().registerIncomingPluginChannel(javaPlugin, "GalaxySkins", this);
        this.initOutgoingChannel("GalaxyGroup");
        this.initOutgoingChannel("GalaxyCustomization");
        this.initOutgoingChannel("GalaxySettings");
    }

    private void initOutgoingChannel(String name) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.javaPlugin, name);
    }

    @EventHandler
    public void onChangeGroup(GamerChangeGroupEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null || !player.isOnline())
            return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(e.getGamer().getPlayerID());
        out.writeInt(e.getGroup().getLevel());
        player.sendPluginMessage(this.javaPlugin, "GalaxyGroup", out.toByteArray());
    }

    @EventHandler
    public void onChangePrefix(GamerChangeCustomizationEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null || !player.isOnline())
            return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(e.getGamer().getPlayerID());

        if (e.getCustomizationType() == CustomizationType.PREFIX_COLOR) {
            out.writeInt(CustomizationType.PREFIX_COLOR.ordinal());
            out.writeInt(e.getGamer().getPrefixColor().getID());
        } else {
            out.writeInt(CustomizationType.TITUL.ordinal());
            out.writeInt(e.getGamer().getSelectedTitul().getId());
        }

        player.sendPluginMessage(this.javaPlugin, "GalaxyCustomization", out.toByteArray());
    }

    @EventHandler
    public void onChangeSettings(GamerChangeSettingEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null)
            return;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(e.getGamer().getPlayerID());
        out.writeInt(e.getSetting().getID());
        out.writeBoolean(e.isEnable());
        player.sendPluginMessage(this.javaPlugin, "GalaxySettings", out.toByteArray());
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("GalaxySkins"))
            return;

        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);
        BukkitGamer gamer = GamerManager.getGamer(input.readInt());
        if (gamer == null || !gamer.isOnline())
            return;

        SkinUtil.setSkin(gamer, input.readUTF(), input.readUTF(),
                input.readUTF(), SkinType.getSkinType(input.readInt()));
    }
}

