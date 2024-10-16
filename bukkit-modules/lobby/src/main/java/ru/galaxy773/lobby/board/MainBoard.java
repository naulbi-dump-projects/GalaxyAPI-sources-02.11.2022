package ru.galaxy773.lobby.board;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.api.presents.PresentsManager;
import ru.galaxy773.lobby.config.PresentsConfig;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

public class MainBoard extends LobbyBoard {

    private static final CoreAPI CORE_API = BukkitAPI.getCoreAPI();
    private static final PresentsConfig PRESENTS_CONFIG = Lobby.getInstance().getConfig(PresentsConfig.class);
    private static final PresentsManager PRESENTS_MANAGER = PRESENTS_CONFIG == null ? null : PRESENTS_CONFIG.getPresentsManager();

    public MainBoard(Player owner) {
        super(owner);
        this.board.setLine(15, StringUtil.getLineCode(15));
        this.board.setLine(13, StringUtil.getLineCode(13));
        this.board.setLine(12, Lang.getMessage("LOBBY_BOARD_PROFILE_LINE"));
        this.board.setLine(7, StringUtil.getLineCode(7));
        this.board.setLine(5, StringUtil.getLineCode(5));
        this.board.setLine(3, StringUtil.getLineCode(3));
        this.board.setLine(2, Lang.getMessage("LOBBY_BOARD_SITE_LINE"));
        this.board.updater(() -> {
            this.board.setDynamicLine(16, Lang.getMessage("LOBBY_BOARD_DATE_LINE"), StringUtil.getCurrentDate());
            this.board.setDynamicLine(14, Lang.getMessage("LOBBY_BOARD_GROUP_LINE"), this.gamer.getChatPrefix());
            this.board.setDynamicLine(11, Lang.getMessage("LOBBY_BOARD_LEVEL_LINE"), String.valueOf(this.gamer.getLevel()));
            this.board.setDynamicLine(10, Lang.getMessage("LOBBY_BOARD_COINS_LINE"), StringUtil.getNumberFormat(this.gamer.getCoins()));
            this.board.setDynamicLine(9, Lang.getMessage("LOBBY_BOARD_GOLD_LINE"), StringUtil.getNumberFormat(this.gamer.getGold()));
            if (PRESENTS_MANAGER != null) {
                this.board.setDynamicLine(8, Lang.getMessage("LOBBY_BOARD_PRESENTS_LINE"), "\u00a7e" + PRESENTS_MANAGER.getFindedCount(owner) + "\u00a78/\u00a7a" + PRESENTS_MANAGER.getMaxCount());
            }
            this.board.setDynamicLine(6, Lang.getMessage("LOBBY_BOARD_BOOSTER_LINE"), String.valueOf(this.gamer.getGroup() == Group.DEFAULT ? "\u043d\u0435\u0442" : Double.valueOf(this.gamer.getGroup().getMultiple())));
            this.board.setDynamicLine(4, Lang.getMessage("LOBBY_BOARD_GLOBAL_ONLINE_LINE"), StringUtil.getNumberFormat(CORE_API.getCoreOnline()));
        }, 60L);
    }
}

