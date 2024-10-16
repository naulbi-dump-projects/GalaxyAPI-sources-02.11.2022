package ru.galaxy773.bukkit.api.gamer.leveling;

import gnu.trove.map.TIntObjectMap;

import java.util.List;

public interface Leveling {

    List<LevelRewardStorage> getRewardsSorted();

    void addReward(int level, LevelReward... levelRewards);

    TIntObjectMap<LevelRewardStorage> getRewards();
}
