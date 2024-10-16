package ru.galaxy773.multiplatform.impl.gamer.sections;

import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.util.Map;

public class SettingsSection extends Section {

    private Map<SettingsType, Boolean> settings;

    public SettingsSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        this.settings = PlayerInfoLoader.getSettings(super.baseGamer.getPlayerID());

        for (Map.Entry<SettingsType, Boolean> entry : settings.entrySet()) {
            if (!entry.getValue())
                continue;

            if (!this.baseGamer.hasChildGroup(entry.getKey().getGroup()))
                this.settings.put(entry.getKey(), false);
        }

        settings.putIfAbsent(SettingsType.CHAT, true);
        settings.putIfAbsent(SettingsType.SCOREBOARD, true);
        settings.putIfAbsent(SettingsType.CLANS_REQUEST, true);
        settings.putIfAbsent(SettingsType.FRIENDS_REQUEST, true);
        settings.putIfAbsent(SettingsType.PRIVATE_MESSAGE, true);
        settings.putIfAbsent(SettingsType.BOSSBAR, true);
        settings.putIfAbsent(SettingsType.AUTOMESSAGES, true);

        if (this.baseGamer.hasChildGroup(Group.WARRIOR)) {
            settings.putIfAbsent(SettingsType.BLOOD, true);
            settings.putIfAbsent(SettingsType.DONATE_CHAT, true);
        }

        return true;
    }

    public boolean getSetting(SettingsType settingsType) {
        return settings.getOrDefault(settingsType, false);
    }

    public void setSetting(SettingsType settingType, boolean flag, boolean saveToMySql) {
        this.settings.put(settingType, flag);
        if (saveToMySql)
            PlayerInfoLoader.setSetting(super.baseGamer.getPlayerID(), settingType, flag);
    }
}
