package ru.galaxy773.multiplatform.api.gamer.friend;

import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;

public interface Friend {

    int getPlayerID();

    boolean isOnline();

    <T extends IBaseGamer> T getGamer();
}
