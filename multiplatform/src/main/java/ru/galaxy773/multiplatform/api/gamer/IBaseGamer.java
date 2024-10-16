package ru.galaxy773.multiplatform.api.gamer;

import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.impl.gamer.sections.Section;
import ru.galaxy773.multiplatform.impl.skin.Skin;

import java.util.Map;

public interface IBaseGamer {

    int getPlayerID();

    String getName();

    String getDisplayName();

    void setDisplayName(String displayName);

    Group getGroup();

    void setGroup(Group group, boolean saveToMySql);

    Skin getSkin();

    void setSkin(Skin skin, boolean saveToMySql);

    <T> T getMetadata(String key);

    void addMetadata(String key, Object metadata);

    void removeMetadata(String key);

    Map<String, Object> getMetadata();

    int getExp();

    int getLevel();

    int getExpNextLevel();

    int getTotalExpNextLevel();

    boolean addExp(int exp);

    void setExp(int exp, boolean saveToMySql);

    int getCoins();

    void setCoins(int coins, boolean saveToMySql);

    int getGold();

    void setGold(int gold, boolean saveToMySql);

    int getKeys(KeysType keyType);

    void setKeys(KeysType keyType, int keys, boolean saveToMySql);

    int getPlayTime();

    long getLastJoin();

    String getLastServer();

    void setLastOnline(long lastOnline, String lastServer);

    double getMultiple();

    boolean hasChildGroup(Group group);

    boolean isStaff();

    boolean isOnline();

    Map<String, Section> getSections();

    default <T extends Section> T getSection(String className) {
        return (T) this.getSections().get(className);
    }

    default <T extends Section> T getSection(Class<T> sectionClass) {
        return (T) this.getSection(sectionClass.getSimpleName());
    }
}
