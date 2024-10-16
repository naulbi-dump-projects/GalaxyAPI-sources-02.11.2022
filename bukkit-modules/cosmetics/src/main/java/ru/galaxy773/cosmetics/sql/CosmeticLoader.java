package ru.galaxy773.cosmetics.sql;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.cosmetics.api.EffectType;
import ru.galaxy773.cosmetics.api.player.CosmeticPlayer;
import ru.galaxy773.cosmetics.data.CachedParticleEffects;
import ru.galaxy773.cosmetics.player.CraftCosmeticPlayer;
import ru.galaxy773.multiplatform.api.sql.columntype.ColumnType;
import ru.galaxy773.multiplatform.api.sql.query.QuerySymbol;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;
import ru.galaxy773.multiplatform.impl.sql.query.MysqlQuery;
import ru.galaxy773.multiplatform.impl.sql.table.TableColumn;
import ru.galaxy773.multiplatform.impl.sql.table.TableConstructor;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public final class CosmeticLoader {

    private final MySqlDatabase MYSQL_DATABASE;

    public MySqlDatabase getMySql() {
        return MYSQL_DATABASE;
    }

    private void createTables() {
        new TableConstructor("player_effects",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("type", ColumnType.INT_5).setDefaultValue(1),
                new TableColumn("effect", ColumnType.INT_5)
        ).create(MYSQL_DATABASE);
        new TableConstructor("player_selected_effects",
                new TableColumn("player_id", ColumnType.INT_11).setDefaultValue(-1),
                new TableColumn("type", ColumnType.INT_5).setDefaultValue(1),
                new TableColumn("effect", ColumnType.INT_5)
        ).create(MYSQL_DATABASE);
    }
    
    public CosmeticPlayer getCosmeticPlayer(Player player, int playerID) {
        return new CraftCosmeticPlayer(player, CosmeticLoader.getEffects(playerID), CosmeticLoader.getSelectedEffects(playerID));
    }

    public Multimap<EffectType, ParticleEffect> getEffects(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_effects").where("player_id", QuerySymbol.EQUALLY, playerID), rs -> {
            Multimap<EffectType, ParticleEffect> data = MultimapBuilder.enumKeys(EffectType.class).enumSetValues(ParticleEffect.class).build();
            while (rs.next()) {
                data.get(EffectType.getById(rs.getInt(2))).add(CachedParticleEffects.getById(rs.getInt(3)));
            }
            return data;
        });
    }

    public Map<EffectType, ParticleEffect> getSelectedEffects(int playerID) {
        return MYSQL_DATABASE.executeQuery(MysqlQuery.selectFrom("player_selected_effects").where("player_id", QuerySymbol.EQUALLY, playerID).limit(EffectType.values().length), rs -> {
            Map<EffectType, ParticleEffect> data = new HashMap<>();
            while (rs.next()) {
                data.put(EffectType.getById(rs.getInt(2)), CachedParticleEffects.getById(rs.getInt(3)));
            }
            return data;
        });
    }

    public void addEffect(int playerID, EffectType effectType, ParticleEffect particleEffect) {
        MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_effects").set("player_id", playerID).set("type", effectType.getId()).set("effect", particleEffect.getId()));
    }

    public void setSelectedEffect(int playerID, EffectType effectType, ParticleEffect particleEffect, boolean insert) {
        if (particleEffect == null) {
            MYSQL_DATABASE.execute(MysqlQuery.deleteFrom("player_selected_effects").where("player_id", QuerySymbol.EQUALLY, playerID).where("type", QuerySymbol.EQUALLY, effectType.getId()).limit());
            return;
        }
        if (insert) {
            MYSQL_DATABASE.execute(MysqlQuery.insertTo("player_selected_effects").set("player_id", playerID).set("type", effectType.getId()).set("effect", particleEffect.getId()));
            return;
        }
        MYSQL_DATABASE.execute(MysqlQuery.update("player_selected_effects").where("player_id", QuerySymbol.EQUALLY, playerID).where("type", QuerySymbol.EQUALLY, effectType.getId()).set("effect", particleEffect.getId()).limit());
    }
    
    static {
        MYSQL_DATABASE = MySqlDatabase.builder()
                .host(MySqlDatabase.HOST)
                .database("cosmetics")
                .user(MySqlDatabase.USER)
                .password(MySqlDatabase.PASSWORD)
                .build();
        
    }
}

