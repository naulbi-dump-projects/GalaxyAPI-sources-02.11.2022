package ru.galaxy773.multiplatform.impl.loader;

import com.google.gson.JsonParser;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import lombok.experimental.UtilityClass;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;
import ru.galaxy773.multiplatform.api.gamer.data.NetworkingType;
import ru.galaxy773.multiplatform.api.gamer.friend.FriendAction;
import ru.galaxy773.multiplatform.api.skin.SkinType;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.api.sql.query.QuerySymbol;
import ru.galaxy773.multiplatform.impl.skin.Skin;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;
import ru.galaxy773.multiplatform.impl.sql.query.MysqlQuery;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

import java.util.*;

@UtilityClass
public class PlayerInfoLoader {

    private final MySqlDatabase MYSQL_DATABASE;
    private final JsonParser JSON_PARSER = new JsonParser();

    public MySqlDatabase getMySql() {
        return MYSQL_DATABASE;
    }

    public Skin getSkin(int playerID) {
        return MYSQL_DATABASE.executeQuery("SELECT * FROM player_skins WHERE player_id = ? LIMIT 1;",
                rs -> {
                    if (rs.next()) {
                        return new Skin(rs.getString(2), rs.getString(3), rs.getString(4), SkinType.getSkinType(rs.getInt(5)));
                    }
                    return Skin.DEFAULT_SKIN;
                }, playerID);
    }

    public void setSkin(int playerID, Skin skin) {
        if (skin.getSkinName().equals(Skin.DEFAULT_SKIN.getSkinName())) {
            MYSQL_DATABASE.execute("DELETE FROM player_skins WHERE player_id = ? LIMIT 1;", playerID);
            return;
        }
        MYSQL_DATABASE.execute(
                "INSERT INTO player_skins (player_id, name, value, signature, type) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                        " name = ?, value = ?, signature = ?, type = ?;",
                playerID, skin.getSkinName(), skin.getValue(), skin.getSignature(), skin.getSkinType().getTypeId(),
                skin.getSkinName(), skin.getValue(), skin.getSignature(), skin.getSkinType().getTypeId());
    }

    public Map<Group, Integer> getGrantedDonates(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_granter")
                        .where("player_id", QuerySymbol.EQUALLY, playerID),
                rs -> {
                    Map<Group, Integer> grantedDonates = new EnumMap<>(Group.class);
                    while (rs.next()) {
                        grantedDonates.put(Group.getGroupByLevel(rs.getInt(2)), rs.getInt(3));
                    }
                    return grantedDonates;
                });
    }

    public void addGrantedDonate(int playerID, Group group, int count, boolean insert) {
        if (insert) {
            MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_granter")
                    .set("player_id", playerID)
                    .set("group_id", group.getLevel())
                    .set("count", count));
            return;
        }
        MYSQL_DATABASE.execute(MysqlQuery.update("player_granter")
                .where("player_id", QuerySymbol.EQUALLY, playerID)
                .where("group_id", QuerySymbol.EQUALLY, group.getLevel())
                .set("count", count)
                .limit());
    }

    public Map<KeysType, Integer> getKeys(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_keys")
                        .where("player_id", QuerySymbol.EQUALLY, playerID)
                        .limit(KeysType.values().length),
                rs -> {
                    Map<KeysType, Integer> keys = new EnumMap<>(KeysType.class);
                    while (rs.next()) {
                        keys.put(KeysType.getKeyType(rs.getInt(2)), rs.getInt(3));
                    }
                    return keys;
                });
    }

    //titul, join_message, quit_message, prefix_color
    public List<Integer> getCustomization(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_customization")
                        .where("player_id", QuerySymbol.EQUALLY, playerID)
                        .limit(),
                rs -> {
                    if (rs.next()) {
                        return Arrays.asList(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
                    } else {
                        return Arrays.asList(-1, -1, -1, 1);
                    }
                });
    }

    public Set<TitulType> getTituls(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_tituls")
                        .where("player_id", QuerySymbol.EQUALLY, playerID),
                rs -> {
                    Set<TitulType> titulTypes = new HashSet<>();
                    while (rs.next()) {
                        titulTypes.add(TitulType.getTitul(rs.getInt(2)));
                    }
                    return titulTypes;
                });
    }

    public Map<SettingsType, Boolean> getSettings(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_settings")
                        .where("player_id", QuerySymbol.EQUALLY, playerID)
                        .limit(SettingsType.values().length),
                rs -> {
                    Map<SettingsType, Boolean> settings = Collections.synchronizedMap(new EnumMap<>(SettingsType.class));
                    while (rs.next()) {
                        settings.put(SettingsType.getSettingType(rs.getInt(2)), rs.getBoolean(3));
                    }
                    return settings;
                });
    }

    public TIntSet getFriends(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_friends")
                        .where("player_id", QuerySymbol.EQUALLY, playerID),
                rs -> {
                    TIntSet friends = new TIntHashSet();
                    while (rs.next()) {
                        friends.add(rs.getInt(2));
                    }
                    return friends;
                });
    }

    //TODO: переписать под нормальныйц insert
    public void setKeys(int playerID, KeysType keyType, int keys) {
        if (keys <= 0) {
            MYSQL_DATABASE.execute(MysqlQuery.deleteFrom("player_keys")
                    .where("player_id", QuerySymbol.EQUALLY, playerID)
                    .where("key_type", QuerySymbol.EQUALLY, keyType.getId())
                    .limit());
            return;
        }
        MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_keys")
                .where("player_id", QuerySymbol.EQUALLY, playerID)
                .where("key_type", QuerySymbol.EQUALLY, keyType.getId())
                .limit(), rs -> {
            if (rs.next()) {
                MYSQL_DATABASE.execute(MysqlQuery.update("player_keys")
                        .where("player_id", QuerySymbol.EQUALLY, playerID)
                        .where("key_type", QuerySymbol.EQUALLY, keyType.getId())
                        .set("keys", keys)
                        .limit());
            } else {
                MYSQL_DATABASE.execute(MysqlQuery
                        .insertTo("player_keys")
                        .set("player_id", playerID)
                        .set("key_type", keyType.getId())
                        .set("keys", keys)
                        .setDuplicate("key_type", keyType.getId()));
            }
            return Void.TYPE;
        });
    }

    //player_id, exp, coins, gold, play_time
    public List<Integer> getNetworking(int playerID) {
        return MYSQL_DATABASE.executeQuery("SELECT * FROM player_networking WHERE player_id = ? LIMIT 1;",
                rs -> {
                    if (rs.next()) {
                        return Arrays.asList(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                    }
                    MYSQL_DATABASE.execute("INSERT INTO player_networking (player_id) VALUES (?);", playerID);
                    return Arrays.asList(0, 0, 0, 0, 0);
                }, playerID);
    }

    public void setNetworkingValue(int playerID, NetworkingType networkingType, int value) {
        MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_networking")
                .set("player_id", playerID)
                .set(networkingType.getColumnName(), value)
                .setDuplicate(networkingType.getColumnName(), value));
        //MYSQL_DATABASE.execute("INSERT INTO player_networking (player_id, ?) VALUES (?, ?) ON DUPLICATE KEY UPDATE ? = ?;", networkingType.getColumnName(), playerID, value, networkingType.getColumnName(), value);
    }

    public void setCustomizationValue(int playerID, CustomizationType customizationType, int value) {
        MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_customization")
                .set("player_id", playerID)
                .set(customizationType.getColumnName(), value)
                .setDuplicate(customizationType.getColumnName(), value));
    }

    public void addTitul(int playerID, TitulType titulType) {
        MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_tituls")
                .set("player_id", playerID)
                .set("titul", titulType.getId())
                .setDuplicate("titul", titulType.getId()));
    }

    public void setSetting(int playerID, SettingsType settingType, boolean flag) {
        MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_settings")
                        .where("player_id", QuerySymbol.EQUALLY, playerID)
                        .where("setting_type", QuerySymbol.EQUALLY, settingType.getID())
                        .limit(),
                rs -> {
                    if (rs.next()) {
                        MYSQL_DATABASE.execute(MysqlQuery.update("player_settings")
                                .where("player_id", QuerySymbol.EQUALLY, playerID)
                                .where("setting_type", QuerySymbol.EQUALLY, settingType.getID())
                                .set("flag", flag ? 1 : 0)
                                .limit());
                    } else {
                        MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_settings")
                                .set("player_id", playerID)
                                .set("setting_type", settingType.getID())
                                .set("flag", flag ? 1 : 0));
                    }
                    return Void.TYPE;
                });
    }

    public void changeFriend(int playerID, int friendID, FriendAction friendAction) {
        if (friendAction == FriendAction.ADD) {
            MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_friends")
                    .set("player_id", playerID)
                    .set("friend_id", friendID));
            return;
        }
        MYSQL_DATABASE.execute(MysqlQuery.deleteFrom("player_friends")
                .where("player_id", QuerySymbol.EQUALLY, playerID)
                .where("friend_id", QuerySymbol.EQUALLY, friendID)
                .limit());
    }

    private void createTables() {
        new TableConstructor("player_skins",
                new TableColumn("player_id", ColumnType.INT_11).unigue(true).primaryKey(true),
                new TableColumn("name", ColumnType.VARCHAR_16).setNull(false),
                new TableColumn("value", ColumnType.TEXT).setNull(false),
                new TableColumn("signature", ColumnType.TEXT).setNull(false),
                new TableColumn("type", ColumnType.INT_2).setDefaultValue(2)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_keys",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("key_type", ColumnType.INT_2),
                new TableColumn("keys", ColumnType.INT_11)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_networking",
                new TableColumn("player_id", ColumnType.INT_11).unigue(true).primaryKey(true).setDefaultValue(-1),
                new TableColumn("exp", ColumnType.BIG_INT).setDefaultValue(0),
                new TableColumn("coins", ColumnType.INT_11).setDefaultValue(0),
                new TableColumn("gold", ColumnType.INT_11).setDefaultValue(0),
                new TableColumn("play_time", ColumnType.INT_11).setDefaultValue(0),
                new TableColumn("level_reward", ColumnType.INT_11).setDefaultValue(0)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_customization",
                new TableColumn("player_id", ColumnType.INT_11).unigue(true).primaryKey(true).setDefaultValue(-1),
                new TableColumn("titul", ColumnType.INT_5).setDefaultValue(-1),
                new TableColumn("join_message", ColumnType.INT_5).setDefaultValue(-1),
                new TableColumn("quit_message", ColumnType.INT_5).setDefaultValue(-1),
                new TableColumn("prefix_color", ColumnType.INT_2).setDefaultValue(1)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_tituls",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("titul", ColumnType.INT_5)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_settings",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("setting_type", ColumnType.INT_2),
                new TableColumn("flag", ColumnType.TINY_INT).setDefaultValue(0)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_friends",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("friend_id", ColumnType.INT_11)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_granter",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("group_id", ColumnType.INT_2),
                new TableColumn("count", ColumnType.INT_2)
        ).create(MYSQL_DATABASE);
    }

    static {
        MYSQL_DATABASE = MySqlDatabase.builder()
                .host(MySqlDatabase.HOST)
                .database("global")
                .user(MySqlDatabase.USER)
                .password(MySqlDatabase.PASSWORD)
                .build();
        createTables();
    }
}
