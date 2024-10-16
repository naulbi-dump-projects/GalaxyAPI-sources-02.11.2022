package ru.galaxy773.multiplatform.impl.gamer;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.OnlineGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.impl.gamer.sections.NetworkingSection;
import ru.galaxy773.multiplatform.impl.gamer.sections.SettingsSection;

public abstract class BaseGamer extends IBaseGamerImpl implements OnlineGamer {

    @Getter
    protected final long startTime;

    public BaseGamer(String name) {
        super(name);
        this.startTime = System.currentTimeMillis();
        this.initSection(SettingsSection.class);
        this.getSections().values().forEach(section -> {
            if (!section.loadData()) {
                throw new IllegalArgumentException("Section " + section.getClass().getSimpleName() + " loading error for player " + name);
            }
        });
    }

    @Override
    public boolean getSetting(SettingsType settingsType) {
        return this.getSection(SettingsSection.class).getSetting(settingsType);
    }

    @Override
    public void setSetting(SettingsType settingsType, boolean flag, boolean saveToMySql) {
        this.getSection(SettingsSection.class).setSetting(settingsType, flag, saveToMySql);
    }

    @Override
    public int getPlayTime() {
        return this.getSection(NetworkingSection.class).getPlayTime() + (int) ((System.currentTimeMillis() - this.startTime) / 1000);
    }

    @Override
    public void setPlayTime(int playTime) {
        this.getSection(NetworkingSection.class).setPlayTime(playTime);
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public int getPlayTimeOnJoin() {
        return (int) (System.currentTimeMillis() - this.startTime) / 1000;
    }
}
