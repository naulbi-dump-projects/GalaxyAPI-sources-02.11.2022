package ru.galaxy773.bukkit.api.tops.armorstand;

import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;

public interface StandTopData {

    Top getTop();

    IBaseGamer getBaseGamer();

    String getTopString();

    int getPosition();
}
