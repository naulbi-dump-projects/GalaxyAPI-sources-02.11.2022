package ru.galaxy773.multiplatform.impl.gamer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.impl.gamer.sections.*;
import ru.galaxy773.multiplatform.impl.skin.Skin;

import java.util.*;

@ToString
public abstract class IBaseGamerImpl implements IBaseGamer {

    @Getter
    @ToString.Exclude
    protected final String name;
    @Setter
    protected String displayName;
    protected final Map<String, Section> sections;
    @Getter
    protected final Map<String, Object> metadata;

    public IBaseGamerImpl(String name) {
        this.name = name;
        this.sections = new LinkedHashMap<>();
        this.metadata = new HashMap<>();
        this.initSection(BaseSection.class);
        this.initSection(SkinSection.class);
        if (this.initSections() != null) {
            this.initSections().forEach(this::initSection);
        }
    }

    protected Set<Class<? extends Section>> initSections() {
        return null;
    }

    @Override
    public int getPlayerID() {
        return this.getSection(BaseSection.class).getPlayerID();
    }

    @Override
    public String getDisplayName() {
        return this.getGroup().getChatPrefix() + this.name;
    }

    @Override
    public Group getGroup() {
        return this.getSection(BaseSection.class).getGroup();
    }

    @Override
    public void setGroup(Group group, boolean saveToMySql) {
        this.getSection(BaseSection.class).setGroup(group, saveToMySql);
    }

    @Override
    public Skin getSkin() {
        return this.getSection(SkinSection.class).getSkin();
    }

    @Override
    public void setSkin(Skin skin, boolean saveToMySql) {
        this.getSection(SkinSection.class).updateSkin(skin, saveToMySql);
    }

    @Override
    public <T> T getMetadata(String key) {
        return (T) this.metadata.get(key);
    }

    @Override
    public void addMetadata(String key, Object metadata) {
        this.metadata.put(key, metadata);
    }

    @Override
    public void removeMetadata(String key) {
        this.metadata.remove(key);
    }

    @Override
    public int getExp() {
        return this.getSection(NetworkingSection.class).getExp();
    }

    @Override
    public int getLevel() {
        return this.getSection(NetworkingSection.class).getLevel();
    }

    @Override
    public int getExpNextLevel() {
        return this.getSection(NetworkingSection.class).getExpNextLevel();
    }

    @Override
    public int getTotalExpNextLevel() {
        return this.getSection(NetworkingSection.class).getTotalExpNextLevel();
    }

    @Override
    public void setExp(int exp, boolean saveToMySql) {
        this.getSection(NetworkingSection.class).setExp(exp, saveToMySql);
    }

    @Override
    public boolean addExp(int exp) {
        return this.getSection(NetworkingSection.class).addExp(exp);
    }

    @Override
    public int getCoins() {
        return this.getSection(NetworkingSection.class).getCoins();
    }

    @Override
    public void setCoins(int coins, boolean saveToMySql) {
        this.getSection(NetworkingSection.class).setCoins(coins, saveToMySql);
    }

    @Override
    public int getGold() {
        return this.getSection(NetworkingSection.class).getGold();
    }

    @Override
    public void setGold(int gold, boolean saveToMySql) {
        int difference = gold - getGold();
        int multiplied = 0;
        if (difference > 0) {
            multiplied = (int) (difference * this.getGroup().getMultiple());
        }
        this.getSection(NetworkingSection.class).setGold(gold + multiplied, saveToMySql);
    }

    @Override
    public int getPlayTime() {
        return getSection(NetworkingSection.class).getPlayTime();
    }

    @Override
    public long getLastJoin() {
        return this.getSection(BaseSection.class).getLastJoin();
    }

    @Override
    public String getLastServer() {
        return this.getSection(BaseSection.class).getLastServer();
    }

    @Override
    public void setLastOnline(long lastOnline, String lastServer) {
        this.getSection(BaseSection.class).setLastOnline(lastOnline, lastServer);
    }

    @Override
    public double getMultiple() {
        return getGroup().getMultiple();
    }

    @Override
    public boolean hasChildGroup(Group group) {
        return getGroup().getLevel() >= group.getLevel();
    }

    @Override
    public boolean isStaff() {
        return hasChildGroup(Group.HELPER);
    }

    @Override
    public Map<String, Section> getSections() {
        return Collections.unmodifiableMap(this.sections);
    }

    @Override
    public int getKeys(KeysType keyType) {
        return this.getSection(KeysSection.class).getKeys(keyType);
    }

    @Override
    public void setKeys(KeysType keyType, int keys, boolean saveToMySql) {
        this.getSection(KeysSection.class).setKeys(keyType, keys, saveToMySql);
    }

    public <T extends Section> void initSection(Class<? extends Section> clazz) {
        T section = null;
        try {
            section = (T) clazz.getConstructor(IBaseGamer.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (section == null) {
            throw new IllegalArgumentException("Section not found or initializing error: " + clazz.getSimpleName());
        }
        this.sections.put(clazz.getSimpleName(), section);
    }
}
