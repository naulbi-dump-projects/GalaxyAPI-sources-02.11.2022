package ru.galaxy773.boxes.api;

import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;

import java.util.List;

public interface ItemManager {

    List<BoxReward> getRewards(KeysType keysType);

    void addBoxReward(KeysType keysType, BoxReward reward);

    void removeBoxReward(KeysType keysType, BoxReward reward);

    void removeBoxRewards(KeysType keysType);
}

