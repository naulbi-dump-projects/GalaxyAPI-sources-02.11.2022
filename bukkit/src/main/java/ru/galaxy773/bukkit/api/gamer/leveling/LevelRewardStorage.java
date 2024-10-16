package ru.galaxy773.bukkit.api.gamer.leveling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import io.netty.util.internal.ConcurrentSet;

import java.util.*;

@RequiredArgsConstructor
public final class LevelRewardStorage {

    @Getter
    private final int level;
    private final Set<LevelReward> rewards = new ConcurrentSet<>();

    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        rewards.stream()
                .sorted(Comparator.comparingInt(LevelReward::getPriority))
                .forEach(levelReward -> lore.add(" " + levelReward.getLore()));
        return lore;
    }

    public void addLevelRewards(LevelReward... levelRewards) {
        rewards.addAll(Arrays.asList(levelRewards));
    }

    public void giveRewards(BukkitGamer gamer) {
        rewards.forEach(levelReward -> levelReward.giveReward(gamer));
    }
}
