package ru.galaxy773.bukkit.impl.gamer.leveling;

import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import ru.galaxy773.bukkit.api.gamer.leveling.LevelReward;
import ru.galaxy773.bukkit.api.gamer.leveling.LevelRewardStorage;
import ru.galaxy773.bukkit.api.gamer.leveling.Leveling;
import ru.galaxy773.bukkit.api.gamer.leveling.type.GoldLevelReward;
import ru.galaxy773.bukkit.api.gamer.leveling.type.KeysLevelReward;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class LevelingImpl implements Leveling {

    private final TIntObjectMap<LevelRewardStorage> leveling = TCollections.synchronizedMap(new TIntObjectHashMap<>());

    public LevelingImpl() {
        addReward(1, new GoldLevelReward(100));
        addReward(2, new GoldLevelReward(150));
        addReward(3, new GoldLevelReward(200));
        addReward(4, new GoldLevelReward(250));
        addReward(5, new GoldLevelReward(300));
        addReward(6, new GoldLevelReward(350));
        addReward(7, new GoldLevelReward(400));
        addReward(8, new GoldLevelReward(450));
        addReward(9, new GoldLevelReward(500));
        addReward(10, new GoldLevelReward(600), new KeysLevelReward(KeysType.GOLD, 1));
        addReward(11, new GoldLevelReward(700));
        addReward(12, new GoldLevelReward(800));
        addReward(13, new GoldLevelReward(900));
        addReward(14, new GoldLevelReward(1000));
        addReward(15, new GoldLevelReward(1100));
        addReward(16, new GoldLevelReward(1200));
        addReward(17, new GoldLevelReward(1300));
        addReward(18, new GoldLevelReward(1400));
        addReward(19, new GoldLevelReward(1500));
        addReward(20, new GoldLevelReward(1600), new KeysLevelReward(KeysType.GOLD, 1));
        addReward(21, new GoldLevelReward(1700));
        addReward(22, new GoldLevelReward(1800));
        addReward(23, new GoldLevelReward(1900));
        addReward(24, new GoldLevelReward(2000));
        addReward(25, new GoldLevelReward(2100));
        addReward(26, new GoldLevelReward(2200));
        addReward(27, new GoldLevelReward(2300));
        addReward(28, new GoldLevelReward(2400));
        addReward(29, new GoldLevelReward(2500));
        addReward(30, new GoldLevelReward(2600));
    }

    @Override
    public List<LevelRewardStorage> getRewardsSorted() {
        return getRewards().valueCollection().stream()
                .sorted(Comparator.comparingInt(LevelRewardStorage::getLevel))
                .collect(Collectors.toList());
    }

    @Override
    public void addReward(int level, LevelReward... levelRewards) {
        LevelRewardStorage levelRewardStorage = leveling.get(level);
        if (levelRewardStorage == null) {
            levelRewardStorage = new LevelRewardStorage(level);
            leveling.put(level, levelRewardStorage);
        }

        levelRewardStorage.addLevelRewards(levelRewards);
    }

    @Override
    public TIntObjectMap<LevelRewardStorage> getRewards() {
        return new TIntObjectHashMap<>(leveling);
    }
}
