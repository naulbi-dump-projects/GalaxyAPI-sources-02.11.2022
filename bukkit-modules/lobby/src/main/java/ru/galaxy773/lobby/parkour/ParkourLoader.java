package ru.galaxy773.lobby.parkour;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.api.sql.query.QuerySymbol;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;
import ru.galaxy773.multiplatform.impl.sql.query.MysqlQuery;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@UtilityClass
public final class ParkourLoader {

    @Getter
    private MySqlDatabase mySql;

    public void initialize(MySqlDatabase mySql) {
        ParkourLoader.mySql = mySql;
        ParkourLoader.createTables();
    }

    private void createTables() {
        new TableConstructor("lobby_parkour", 
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1), 
                new TableColumn("way", ColumnType.INT_11).setDefaultValue(1), 
                new TableColumn("time", ColumnType.INT_11).setDefaultValue(0)
        ).create(mySql);
    }

    public Map<Integer, Long> getData(int playerID) {
        return mySql.executeQuery(MysqlQuery.selectFrom("lobby_parkour").where("player_id", QuerySymbol.EQUALLY, (Object)playerID), rs -> {
            HashMap<Integer, Long> data = new HashMap<Integer, Long>();
            while (rs.next()) {
                data.put(rs.getInt(2), rs.getLong(3));
            }
            return data;
        });
    }

    public void setData(int playerID, int wayID, long time) {
        mySql.executeQuery(MysqlQuery.selectFrom("lobby_parkour").where("player_id", QuerySymbol.EQUALLY, playerID).where("way", QuerySymbol.EQUALLY, wayID).limit(), rs -> {
            if (rs.next()) {
                mySql.execute(MysqlQuery.update("lobby_parkour").where("player_id", QuerySymbol.EQUALLY, playerID).where("way", QuerySymbol.EQUALLY, wayID).set("time", time).limit());
            } else {
                mySql.execute(MysqlQuery.insertTo("lobby_parkour").set("player_id", playerID).set("way", wayID).set("time", time));
            }
            return Void.TYPE;
        });
    }

    public Map<IBaseGamer, Long> getTop(int wayID, int limit) {
        return mySql.executeQuery("SELECT * FROM lobby_parkour WHERE way = " + wayID + " ORDER BY time LIMIT 10", rs -> {
            Map<IBaseGamer, Long> levelTop = new LinkedHashMap<>();
            while (rs.next()) {
                IBaseGamer baseGamer;
                if (rs.getInt(1) == -1 || (baseGamer = GamerManager.getFromCacheOrLoad(rs.getInt(1))) == null)
                    continue;

                levelTop.put(baseGamer, rs.getLong(3));
            }
            return levelTop;
        });
    }
}

