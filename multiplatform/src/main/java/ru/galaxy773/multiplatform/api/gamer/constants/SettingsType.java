package ru.galaxy773.multiplatform.api.gamer.constants;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SettingsType {

    HIDER(1, Group.DEFAULT),
    BLOOD(2, Group.WARRIOR),
    FLY(3, Group.LEGEND),
    CHAT(4, Group.DEFAULT),
    SCOREBOARD(5, Group.DEFAULT),
    PRIVATE_MESSAGE(6, Group.DEFAULT),
    FRIENDS_REQUEST(7, Group.DEFAULT),
    CLANS_REQUEST(8, Group.DEFAULT),
    DONATE_CHAT(9, Group.WARRIOR),
    BOSSBAR(10, Group.LORD),
    AUTOMESSAGES(11, Group.KING);

    private final int id;
    @Getter
    private final Group group;

    public int getID() {
        return this.id;
    }

    private static final TIntObjectMap<SettingsType> SETTINGS = new TIntObjectHashMap<>();
    public static SettingsType getSettingType(int id) {
        return SETTINGS.get(id);
    }

    static {
        Arrays.stream(values()).forEach(settingType -> SETTINGS.put(settingType.getID(), settingType));
    }
}
