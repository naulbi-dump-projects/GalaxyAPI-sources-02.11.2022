package ru.galaxy773.bukkit.api.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by Galaxy773 04.08.2021
 */
@AllArgsConstructor
public enum SubType {

    //думаю, сделать бы тут нормально все когда нибудь...
    AUTH(null, "auth", "Auth", "AUTH_SERVER_TYPE", GameType.AUTH, GameType.AUTH.getIcon(), null),
    HUB(null, "hub", "Hub", "LOBBY_SERVER_TYPE", GameType.HUB, GameType.HUB.getIcon(), null),
    SURVIVAL(null, "survival", "Survival", "SURVIVAL_SERVER_TYPE", GameType.SURVIVAL, GameType.SURVIVAL.getIcon(), null),
    ONEBLOCK(null, "oneblock", "OneBlock", "ONEBLOCK_SERVER_TYPE", GameType.ONEBLOCK, GameType.ONEBLOCK.getIcon(), null),
    LW_LOBBY(null, "lw-lobby", "LuckyWars", "LUCKYWARS_LOBBY_TYPE", GameType.LOBBY, GameType.LW.getIcon(), null),
    LW_SOLO(TypeGame.SOLO, "lw-s", "LuckyWars Solo", "LUCKYWARS_SOLO_TYPE", GameType.LW, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM5Nzg0M2ZkM2E2ODUyOGEyODY3NDE2OWI5YjgzMWMxNjU4ZmIxZDg2YTcyMTgyNmRjZWQ1MDM0MmUzMjVmMiJ9fX0="), new TableConstructor("lw_solo", new TableColumn("player_id", ColumnType.INT_11).primaryKey(true), new TableColumn("type", ColumnType.VARCHAR_16).primaryKey(true).setDefaultValue("all"), new TableColumn("wins", ColumnType.INT_11).setDefaultValue(0), new TableColumn("kills", ColumnType.INT_11).setDefaultValue(0), new TableColumn("blocks", ColumnType.INT_11).setDefaultValue(0), new TableColumn("winstreak_current", ColumnType.INT_11).setDefaultValue(0), new TableColumn("winstreak_highest", ColumnType.INT_11).setDefaultValue(0))),
    LW_DOUBLES(TypeGame.DOUBLES, "lw-d", "LuckyWars Doubles", "LUCKYWARS_DOUBLES_TYPE", GameType.LW, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjExODU2MTVkNWNjN2M3MDBlYWJjYjdkYjA5N2VkNzIxZDU4OWZkZmVlZjlmMDMzMzM2YzI2Yzk4OGU0YmU0NiJ9fX0="), new TableConstructor("lw_doubles", new TableColumn("player_id", ColumnType.INT_11).primaryKey(true), new TableColumn("type", ColumnType.VARCHAR_16).primaryKey(true).setDefaultValue("all"), new TableColumn("wins", ColumnType.INT_11).setDefaultValue(0), new TableColumn("kills", ColumnType.INT_11).setDefaultValue(0), new TableColumn("blocks", ColumnType.INT_11).setDefaultValue(0), new TableColumn("winstreak_current", ColumnType.INT_11).setDefaultValue(0), new TableColumn("winstreak_highest", ColumnType.INT_11).setDefaultValue(0))),
    SW_LOBBY(null, "sw-lobby", "SkyWars", "SKYWARS_LOBBY_TYPE", GameType.LOBBY, GameType.SW.getIcon(), null),
    SW_SOLO(TypeGame.SOLO, "sw-s", "SkyWars Solo", "SKYWARS_SOLO_TYPE", GameType.SW, GameType.SW.getIcon(), null),
    SW_DOUBLES(TypeGame.DOUBLES, "sw-d", "SkyWars Doubles", "SKYWARS_DOUBLES_TYPE", GameType.SW, GameType.SW.getIcon(), null),
    BW_LOBBY(null, "bw-lobby", "BedWars", "BEDWARS_LOBBY_TYPE", GameType.LOBBY, GameType.BW.getIcon(), null),
    BW_SOLO(TypeGame.SOLO, "bw-s", "BedWars Solo", "BEDWARS_SOLO_TYPE", GameType.BW, GameType.BW.getIcon(), null),
    BW_DOUBLES(TypeGame.DOUBLES, "bw-d", "BedWars Doubles", "BEDWARS_DOUBLES_TYPE", GameType.BW, GameType.BW.getIcon(), null),
    BW_TEAM(TypeGame.TEAM, "bw-t", "BedWars Team", "BEDWARS_TEAM_TYPE", GameType.BW, GameType.BW.getIcon(), null);

    @Getter
    @Setter
    @Nullable
    private static SubType current = AUTH;

    @Getter
    private final TypeGame typeGame;
    @Getter
    private final String folderName;
    @Getter
    private final String typeName;
    @Getter
    private final String localeName;
    @Getter
    private final GameType gameType;
    private final ItemStack icon;
    @Getter
    @Nullable
    private final TableConstructor tableConstructor;

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    public static boolean isMisc() {
        return getCurrent() == SURVIVAL || getCurrent() == ONEBLOCK;
    }

    public static boolean isHub() {
        return getCurrent() == HUB;
    }

    public static boolean isGameLobby() {
        return getCurrent() == LW_LOBBY;
    }

    public static boolean isGame() {
        return !isMisc() && !isHub() && !isGameLobby();
    }

    public static List<SubType> ofMode(GameType type) {
        return Arrays.stream(values())
                .filter(subType -> subType.getGameType() == type)
                .collect(Collectors.toList());
    }

    public static SubType tryGet(String folderName) {
        folderName = folderName.toLowerCase();
        String finalFolderName = folderName;
        return Arrays.stream(values())
                .filter(subType -> finalFolderName.startsWith(subType.getFolderName()))
                .findFirst()
                .orElse(null);
    }

    public static SubType getByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException ignore) {
            return null;
        }
    }
}
