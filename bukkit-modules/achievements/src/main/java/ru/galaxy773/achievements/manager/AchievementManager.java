package ru.galaxy773.achievements.manager;

import lombok.Getter;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.data.AchievementData;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class AchievementManager {

    private final MySqlDatabase mySql;
    private final Map<String, AchievementPlayer> playersCache;
    
    public AchievementManager() {
        this.mySql = MySqlDatabase.builder()
                .host(MySqlDatabase.HOST)
                .database("global")
                .user(MySqlDatabase.USER)
                .password(MySqlDatabase.PASSWORD)
                .build();
        this.playersCache = new ConcurrentHashMap<>();
        this.createTables();
    }

    public AchievementPlayer getFromCache(String playerName) {
        return this.playersCache.get(playerName);
    }

    public AchievementPlayer getFromCacheOrLoad(String playerName) {
        AchievementPlayer achievementPlayer = this.playersCache.get(playerName);

        if (achievementPlayer == null) {
            BukkitGamer gamer = GamerManager.getGamer(playerName);
            if (gamer == null)
                throw new RuntimeException("Error when achievement player loading because gamer not found");

            achievementPlayer = new AchievementPlayer(playerName, getAchievements(gamer.getPlayerID()));
            this.playersCache.put(playerName, achievementPlayer);
        }

        return achievementPlayer;
    }

    public void removeFromCache(String playerName) {
        this.playersCache.remove(playerName);
    }

    public Map<AchievementType, AchievementData> getAchievements(int playerID) {
        return this.mySql.executeQuery("SELECT * FROM player_achievements WHERE player_id = ?;",
                rs -> {
                    Map<AchievementType, AchievementData> achievements = new EnumMap<>(AchievementType.class);

                    while (rs.next()) {
                        AchievementData data = new AchievementData(rs.getInt(4), rs.getLong(5));
                        achievements.put(AchievementType.getById(rs.getInt(3)), data);
                    }

                    return achievements;
                }, playerID);
    }

    public void saveAchievements(int playerID, Map<AchievementType, AchievementData> achievements) {
        for (Map.Entry<AchievementType, AchievementData> entry : achievements.entrySet()) {
            AchievementData data = entry.getValue();
            this.mySql.execute("INSERT INTO player_achievements (player_id, achievement_id, progress, date) " +
                    "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE progress = ?, date = ?;",
                    playerID, entry.getKey().getId(), data.getProgress(),
                    data.getCompletedDate(), data.getProgress(), data.getCompletedDate());
        }
    }

    private void createTables() {
        new TableConstructor("player_achievements",
                new TableColumn("id", ColumnType.INT_11).autoIncrement(true).unigue(true),
                new TableColumn("player_id", ColumnType.INT_11).primaryKey(true),
                new TableColumn("achievement_id", ColumnType.INT_11).primaryKey(true),
                new TableColumn("progress", ColumnType.INT_11).setDefaultValue(0),
                new TableColumn("date", ColumnType.BIG_INT).setDefaultValue(-1)
        ).create(this.mySql);
    }
}
