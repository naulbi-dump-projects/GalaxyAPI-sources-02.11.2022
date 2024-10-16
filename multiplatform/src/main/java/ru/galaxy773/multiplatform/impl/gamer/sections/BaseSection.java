package ru.galaxy773.multiplatform.impl.gamer.sections;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.impl.loader.GlobalLoader;

@Getter
public class BaseSection extends Section {

    private int playerID;
    private Group group;
    private long lastJoin;
    private String lastServer;

    public BaseSection(IBaseGamer baseGamer) {
        super(baseGamer);
        this.playerID = -1;
    }

    @Override
    public boolean loadData() {
        this.playerID = GlobalLoader.getPlayerID(super.baseGamer.getName());
        this.group = GlobalLoader.getGroup(this.baseGamer.getName());//GlobalLoader.getGroup(this.playerID);
        Pair<Long, String> lastOnline = GlobalLoader.getLastJoin(this.getPlayerID());
        this.lastJoin = lastOnline.getKey();
        this.lastServer = lastOnline.getValue();
        return this.playerID != -1;
    }

    public void setGroup(Group group, boolean saveToMySql) {
        if (group == null || this.group == group)
            return;

        this.group = group;
    }

    public void setLastOnline(long lastJoin, String lastServer) {
        this.lastJoin = lastJoin;
        this.lastServer = lastServer;
        GlobalLoader.setLastJoin(this.baseGamer.getPlayerID(), lastJoin, lastServer);
    }
}
