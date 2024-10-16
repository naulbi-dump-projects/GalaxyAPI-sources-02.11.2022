package ru.galaxy773.multiplatform.impl.loader;

import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;
import ru.galaxy773.multiplatform.impl.sql.StatementWrapper;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.UUID;

@UtilityClass
public class GlobalLoader {

    private final MySqlDatabase MYSQL_DATABASE;
    private final JsonParser JSON_PARSER;

    public MySqlDatabase getMySql() {
        return MYSQL_DATABASE;
    }

    public int getPlayerID(String name) {
        return MYSQL_DATABASE.executeQuery("SELECT `player_id` FROM `identifiers` WHERE `player_name` = ? LIMIT 1;", rs -> rs.next() ?
                rs.getInt(1) :
                StatementWrapper.create(GlobalLoader.MYSQL_DATABASE, "INSERT INTO `identifiers` (`player_name`) VALUES (?);").execute(1, name), name);
        /*return MYSQL_DATABASE.executeQuery("SELECT player_id FROM identifiers WHERE player_name = ? LIMIT 1;",
                rs -> rs.next() ? rs.getInt("player_id") : -1, name.toLowerCase());*/
    }

    public boolean containsPlayerID(String name) {
        return MYSQL_DATABASE.executeQuery("SELECT `player_id` FROM `identifiers` WHERE `player_name` = ? LIMIT 1;",
                ResultSet::next, name);
    }

    public String getPlayerName(int playerID) {
        return MYSQL_DATABASE.executeQuery("SELECT player_name FROM identifiers WHERE player_id = ? LIMIT 1;",
                rs -> rs.next() ? rs.getString("player_name") : null, playerID);
    }

    public Group getGroup(int playerID) {
        if (playerID == -1)
            return Group.DEFAULT;

        return MYSQL_DATABASE.executeQuery("SELECT group_id FROM player_groups WHERE player_id = ? LIMIT 1;",
                rs -> rs.next() ? Group.getGroupByLevel(rs.getInt("group_id")) : Group.DEFAULT, playerID);
    }

    public void setGroup(int playerID, Group group) {
        if (playerID == -1)
            return;

        MYSQL_DATABASE.execute("INSERT INTO player_groups (player_id, group_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE group_id = ?;", playerID, group.getLevel(), group.getLevel());
    }

    public Group getGroup(String playerName) {
        return MYSQL_DATABASE.executeQuery("SELECT * FROM users WHERE name = ? LIMIT 1;", rs -> {
            if (!rs.next()) {
                return Group.DEFAULT;
            }
            else {
                return Group.getGroupByName(JSON_PARSER.parse(rs.getString(2)).getAsJsonObject().get("userGroup").getAsString());
            }
        }, UUID.nameUUIDFromBytes(("SpermUUID:" + playerName.toLowerCase()).getBytes(StandardCharsets.UTF_8)).toString());
    }

    public Pair<Long, String> getLastJoin(int playerID) {
        return MYSQL_DATABASE.executeQuery("SELECT last_join, last_server FROM player_last_join WHERE player_id = ? LIMIT 1;",
                rs -> rs.next() ? Pair.of(rs.getLong("last_join"), rs.getString("last_server")) : Pair.of(System.currentTimeMillis(), "auth-1")
                , playerID);
    }

    public void setLastJoin(int playerID, long lastJoin, String lastServer) {
        MYSQL_DATABASE.execute("INSERT INTO player_last_join (player_id, last_join, last_server) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE last_join = ?, last_server = ?;",
                playerID, lastJoin, lastServer, lastJoin, lastServer);
    }

    private void createTables() {
        new TableConstructor("identifiers",
                new TableColumn("player_id", ColumnType.INT_11).unigue(true).primaryKey(true).autoIncrement(true),
                new TableColumn("player_name", ColumnType.VARCHAR_16).unigue(true).setNull(false)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_groups",
                new TableColumn("player_id", ColumnType.INT_11).unigue(true).primaryKey(true),
                new TableColumn("group_id", ColumnType.INT_5)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_last_join",
                new TableColumn("player_id", ColumnType.INT_11).unigue(true).primaryKey(true),
                new TableColumn("last_join", ColumnType.BIG_INT).setDefaultValue(0),
                new TableColumn("last_server", ColumnType.VARCHAR_32).setNull(false)
        ).create(MYSQL_DATABASE);
    }

    static {
        MYSQL_DATABASE = MySqlDatabase.builder()
                .host(MySqlDatabase.HOST)
                .database("global")
                .user(MySqlDatabase.USER)
                .password(MySqlDatabase.PASSWORD)
                .build();
        JSON_PARSER = new JsonParser();
        createTables();
    }
}
