package ru.galaxy773.lobby.presents;

import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.api.sql.query.QuerySymbol;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;
import ru.galaxy773.multiplatform.impl.sql.query.MysqlQuery;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

@UtilityClass
public final class PresentsLoader {

    @Getter
    private MySqlDatabase mySql;

    public void initialize(MySqlDatabase mySql) {
        PresentsLoader.mySql = mySql;
        PresentsLoader.createTables();
    }

    private void createTables() {
        new TableConstructor("lobby_presents",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("location", ColumnType.BIG_INT).setNull(false)
        ).create(mySql);
    }

    public void addToStore(int playerID, long location) {
        mySql.execute(MysqlQuery.insertTo("lobby_presents").set("player_id", (Object)playerID).set("location", (Object)location));
    }

    public TLongList getStore(int playerID) {
        return mySql.executeQuery(MysqlQuery.selectFrom("lobby_presents").where("player_id", QuerySymbol.EQUALLY, (Object)playerID), rs -> {
            TLongArrayList store = new TLongArrayList();
            while (rs.next()) {
                store.add(rs.getLong("location"));
            }
            return store;
        });
    }
}

