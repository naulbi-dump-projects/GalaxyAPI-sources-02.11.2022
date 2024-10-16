package ru.galaxy773.bukkit.impl.listeners.gamer;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.*;
import ru.Den_Abr.SimplePerms.Bukkit.Events.EntityActionEvent;
import ru.Den_Abr.SimplePerms.Entity.SimpleUser;
import ru.Den_Abr.SimplePerms.EntityAction;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeCustomizationEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeGroupEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerLevelUpEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerLoadSectionEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerPreLoginEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerQuitEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.scoreboard.DisplaySlot;
import ru.galaxy773.bukkit.api.scoreboard.Objective;
import ru.galaxy773.bukkit.api.scoreboard.PlayerTag;
import ru.galaxy773.bukkit.api.scoreboard.ScoreBoardAPI;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.chat.ChatUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.impl.gamer.BukkitGamerImpl;
import ru.galaxy773.multiplatform.api.gamer.GamerAPI;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.customization.JoinMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.QuitMessage;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.impl.gamer.sections.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GamerListener extends GListener<BukkitPlugin> {

    private final ScoreBoardAPI scoreBoardAPI = BukkitAPI.getScoreBoardAPI();
    private final Map<String, BukkitGamer> preLoadedGamers = new ConcurrentHashMap<>();
    private final ImmutableSet<Class<? extends Section>> loadingSections = ImmutableSet.of(
            CustomizationSection.class, NetworkingSection.class,
            KeysSection.class, FriendsSection.class);
    private final Objective levelObjective = scoreBoardAPI.createObjective("level", "dummy");

    public GamerListener(BukkitPlugin plugin) {
        super(plugin);
        this.levelObjective.setDisplayName("§6✩");
        this.levelObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        this.levelObjective.setPublic(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void loadData(AsyncPlayerPreLoginEvent e) {
        GamerAPI.removeOfflineGamer(e.getName());
        BukkitGamer gamer = new BukkitGamerImpl(e);
        BukkitUtil.callEvent(new AsyncGamerPreLoginEvent(gamer, e));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLoadGamer(AsyncGamerPreLoginEvent e) {
        BukkitGamer gamer = e.getGamer();
        this.preLoadedGamers.put(gamer.getName(), gamer);
    }

    @EventHandler
    public void onLoadSection(AsyncGamerLoadSectionEvent e) {
        e.setSections(this.loadingSections);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGlobalLogin(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED)
            return;

        Player player = e.getPlayer();
        BukkitGamerImpl gamer = (BukkitGamerImpl) this.preLoadedGamers.remove(player.getName());
        if (gamer == null) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§c\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0435 \u0434\u0430\u043d\u043d\u044b\u0445");
            return;
        }

        /*if (gamer.getGroup() != Group.ADMIN) {
            PermissionAttachment attachment = player.addAttachment(this.javaPlugin);
            attachment.setPermission("bukkit.command.version", false);
            attachment.setPermission("bukkit.command.plugins", false);
            attachment.setPermission("minecraft.command.help", false);
            attachment.setPermission("bukkit.command.help", false);
            attachment.setPermission("minecraft.command.me", false);
            attachment.setPermission("bukkit.command.me", false);
            attachment.setPermission("minecraft.command.tell", false);
            attachment.setPermission("bukkit.command.tell", false);
        }

        player.setDisplayName(gamer.getDisplayName());*/

        gamer.setPlayer(player);
        GamerAPI.addGamer(gamer);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGlobalJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        BukkitGamer gamer = GamerManager.getGamer(player);
        if (gamer == null) {
            player.kickPlayer("§c\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0435 \u0434\u0430\u043d\u043d\u044b\u0445");
            return;
        }
        BukkitUtil.runTaskAsync(() -> {
            PlayerTag playerTag = this.scoreBoardAPI.createTag(gamer.getGroup().getPriority() + player.getName());
            playerTag.addPlayerToTeam(player);
            playerTag.setPrefix(gamer.getTagPrefix());
            if (gamer.getSelectedTitul() != TitulType.NONE)
                playerTag.setSuffix(" " + gamer.getSelectedTitul().getTitul().getTitul());

            playerTag.disableCollidesForAll();
            this.scoreBoardAPI.setDefaultTag(player, playerTag);

            this.levelObjective.setScore(player, gamer.getLevel());

            BukkitUtil.callEvent(new AsyncGamerJoinEvent(gamer));
            if (gamer.getJoinMessage() != JoinMessage.NONE) {
                String message = String.format(gamer.getJoinMessage().getMessage(), gamer.getDisplayName());
                Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(message));
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        this.scoreBoardAPI.removeDefaultTag(e.getPlayer());
        BukkitGamer gamer = GamerManager.getGamer(e.getPlayer());
        if (gamer != null) {
            BukkitUtil.callEventAsync(new AsyncGamerQuitEvent(gamer));
            if (gamer.getQuitMessage() != QuitMessage.NONE) {
                String message = String.format(gamer.getQuitMessage().getMessage(), gamer.getDisplayName());
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
            }
            gamer.setPlayTime(gamer.getPlayTime());
        }
        GamerManager.removeGamer(e.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGroupChange(GamerChangeGroupEvent e) {
        if (!e.getGamer().isOnline())
            return;

        BukkitGamer gamer = e.getGamer();
        Player player = gamer.getPlayer();
        PlayerTag playerTag = this.scoreBoardAPI.createTag(gamer.getGroup().getPriority() + player.getName());
        playerTag.addPlayerToTeam(player);
        playerTag.setPrefix(gamer.getTagPrefix());
        if (gamer.getSelectedTitul() != TitulType.NONE)
            playerTag.setSuffix(" " + gamer.getSelectedTitul().getTitul().getTitul());

        playerTag.disableCollidesForAll();
        this.scoreBoardAPI.setDefaultTag(player, playerTag);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPrefixColorChange(GamerChangeCustomizationEvent e) {
        if (e.getCustomizationType() != CustomizationType.PREFIX_COLOR)
            return;

        if (!e.getGamer().isOnline())
            return;

        BukkitGamer gamer = e.getGamer();
        Player player = gamer.getPlayer();
        PlayerTag playerTag = this.scoreBoardAPI.getPlayerTag(player);
        playerTag.setPrefix(gamer.getTagPrefix());
        playerTag.sendToAll();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTitulChange(GamerChangeCustomizationEvent e) {
        if (e.getCustomizationType() != CustomizationType.TITUL)
            return;

        if (!e.getGamer().isOnline())
            return;

        BukkitGamer gamer = e.getGamer();
        Player player = gamer.getPlayer();
        PlayerTag playerTag = this.scoreBoardAPI.getPlayerTag(player);
        playerTag.setSuffix(gamer.getSelectedTitul() == TitulType.NONE ? "" : " " + gamer.getSelectedTitul().getTitul().getTitul());
        playerTag.sendToAll();
    }

    @EventHandler
    public void onLevelUp(GamerLevelUpEvent e) {
        e.getGamer().sendMessageLocale("LEVEL_UP", e.getLevel());
        this.levelObjective.setScore(e.getGamer().getPlayer(), e.getLevel());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onColorsTranslate(AsyncPlayerChatEvent event) {
        BukkitGamer gamer = GamerManager.getGamer(event.getPlayer());
        String message = event.getMessage();
        if (gamer.hasChildGroup(Group.ARES)) {
            message = ChatUtil.translateOtherCodes(message);
        }
        if (gamer.hasChildGroup(Group.CHRONOS)) {
            message = ChatUtil.translateColorCodes(message);
        }
        event.setMessage(message);
    }

    @EventHandler
    public void onEntityAction(EntityActionEvent e) {
        if (e.getAction() != EntityAction.PARENTS_CHANGED)
            return;

        if (!(e.getEntity() instanceof SimpleUser))
            return;

        Player player = e.getPlayerIfOnline();
        if (player == null)
            return;

        BukkitGamer gamer = GamerManager.getGamer(player);
        gamer.setGroup(Group.getGroupByName(((SimpleUser)e.getEntity()).getUserGroup().getId()), false);
    }
}