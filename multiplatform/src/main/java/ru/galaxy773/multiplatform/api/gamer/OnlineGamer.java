package ru.galaxy773.multiplatform.api.gamer;

import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.gamer.constants.Version;
import ru.galaxy773.multiplatform.api.locale.Lang;

import java.net.InetAddress;
import java.util.Collection;

public interface OnlineGamer extends IBaseGamer {

    void sendMessage(String message, Object... replace);

    default void sendMessages(Collection<String> messages, Object... replace) {
        messages.forEach(message -> sendMessage(message, replace));
    }

    default void sendMessageLocale(String key, Object... replace) {
        sendMessages(Lang.getList(key, replace));
    }

    void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, Object... replace);

    void sendActionBar(String actionBar, Object... replace);

    Version getVersion();

    InetAddress getIp();

    boolean getSetting(SettingsType settingsType);

    void setSetting(SettingsType settingsType, boolean flag, boolean saveToMySql);

    long getStartTime();

    int getPlayTimeOnJoin();

    void setPlayTime(int playTime);
}
