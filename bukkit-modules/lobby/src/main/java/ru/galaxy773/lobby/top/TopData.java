package ru.galaxy773.lobby.top;

import ru.galaxy773.bukkit.api.tops.armorstand.StandTopData;
import ru.galaxy773.bukkit.api.tops.armorstand.Top;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;

public class TopData
implements StandTopData {
    private final Top top;
    private final IBaseGamer baseGamer;
    private final String topString;
    private final int position;

    public Top getTop() {
        return this.top;
    }

    public IBaseGamer getBaseGamer() {
        return this.baseGamer;
    }

    public String getTopString() {
        return this.topString;
    }

    public int getPosition() {
        return this.position;
    }

    public TopData(Top top, IBaseGamer baseGamer, String topString, int position) {
        this.top = top;
        this.baseGamer = baseGamer;
        this.topString = topString;
        this.position = position;
    }
}

