package ru.galaxy773.multiplatform.impl.gamer.sections;

import lombok.Getter;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.data.NetworkingType;
import ru.galaxy773.multiplatform.api.utils.gamer.GamerUtil;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.util.List;

@Getter
public class NetworkingSection extends Section {

    private int exp;
    private int coins;
    private int gold;
    private int playTime;
    private int lastLevelReward;

    public NetworkingSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        List<Integer> data = PlayerInfoLoader.getNetworking(super.baseGamer.getPlayerID());
        this.exp = data.get(0);
        this.coins = data.get(1);
        this.gold = data.get(2);
        this.playTime = data.get(3);
        this.lastLevelReward = data.get(4);
        return true;
    }

    public int getLevel() {
        return GamerUtil.getLevel(exp);
    }

    public int getExpNextLevel() {
        return GamerUtil.getExpNextLevel(exp, getLevel() + 1);
    }

    public int getTotalExpNextLevel() {
        return GamerUtil.getTotalExpNextLevel(exp, getLevel() + 1);
    }

    public boolean addExp(int exp) {
        int level = getLevel();
        this.exp += exp;
        PlayerInfoLoader.setNetworkingValue(super.baseGamer.getPlayerID(), NetworkingType.EXP, this.exp);
        return level < getLevel();
    }

    public void setCoins(int coins, boolean saveToMySql) {
        this.coins = coins;
        if (saveToMySql) {
            PlayerInfoLoader.setNetworkingValue(super.baseGamer.getPlayerID(), NetworkingType.COINS, coins);
        }
    }

    public void setGold(int gold, boolean saveToMySql) {
        this.gold = gold;
        if (saveToMySql) {
            PlayerInfoLoader.setNetworkingValue(super.baseGamer.getPlayerID(), NetworkingType.GOLD, gold);
        }
    }

    public void setExp(int exp, boolean saveToMySql) {
        this.exp = exp;
        if (saveToMySql) {
            PlayerInfoLoader.setNetworkingValue(super.baseGamer.getPlayerID(), NetworkingType.EXP, exp);
        }
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
        PlayerInfoLoader.setNetworkingValue(super.baseGamer.getPlayerID(), NetworkingType.PLAY_TIME, playTime);
    }

    public void updateLastLevelReward() {
        this.lastLevelReward++;
        PlayerInfoLoader.setNetworkingValue(super.baseGamer.getPlayerID(), NetworkingType.LEVEL_REWARD, this.lastLevelReward);
    }
}
