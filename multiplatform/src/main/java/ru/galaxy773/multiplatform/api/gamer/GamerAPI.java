package ru.galaxy773.multiplatform.api.gamer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.experimental.UtilityClass;
import ru.galaxy773.multiplatform.impl.gamer.BaseGamer;
import ru.galaxy773.multiplatform.impl.gamer.OfflineGamer;
import ru.galaxy773.multiplatform.impl.loader.GlobalLoader;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class GamerAPI {

    private final Map<String, BaseGamer> GAMERS = new ConcurrentHashMap<>();
    private final LoadingCache<String, IBaseGamer> OFFLINE_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build(new CacheLoader<String, IBaseGamer>() {
                @Override
                public OfflineGamer load(String name) throws Exception {
                    return new OfflineGamer(name);
                }
            });

    public Map<String, BaseGamer> getGamers() {
        return GAMERS;
    }

    public IBaseGamer getGamer(String name) {
        return GAMERS.get(name);
    }

    public IBaseGamer getGamer(int playerID) {
        return GAMERS.values()
                .stream()
                .filter(gamer -> gamer.getPlayerID() == playerID)
                .findFirst()
                .orElse(null);
    }

    public void addGamer(BaseGamer gamer) {
        GAMERS.put(gamer.getName(), gamer);
    }

    public void removeGamer(String name) {
        GAMERS.remove(name);
    }

    public void removeGamer(int playerID) {
        IBaseGamer IBaseGamer = getGamer(playerID);
        if (IBaseGamer == null) {
            return;
        }
        removeGamer(IBaseGamer.getName());
    }

    public IBaseGamer getFromCacheOrLoad(String name) {
        IBaseGamer baseGamer = getGamer(name);
        if (baseGamer != null) {
            return baseGamer;
        }
        OFFLINE_CACHE.cleanUp();
        try {
            if (name == null) {
                return null;
            }
            return OFFLINE_CACHE.get(name);
        } catch (ExecutionException e) {
            return null;
        }
    }

    public IBaseGamer getFromCacheOrLoad(int playerID) {
        IBaseGamer baseGamer = getGamer(playerID);
        if (baseGamer != null) {
            return baseGamer;
        }
        OFFLINE_CACHE.cleanUp();
        try {
            String playerName = GlobalLoader.getPlayerName(playerID);
            if (playerName == null) {
                return null;
            }
            return OFFLINE_CACHE.get(playerName);
        } catch (ExecutionException ignored) {
            return null;
        }
    }

    public void removeOfflineGamer(String name) {
        OFFLINE_CACHE.invalidate(name);
    }

    public void removeOfflineGamer(int playerID) {
        IBaseGamer baseGamer = OFFLINE_CACHE.asMap().values()
                .stream()
                .filter(gamer -> gamer.getPlayerID() == playerID)
                .findFirst()
                .orElse(null);
        if (baseGamer == null) {
            return;
        }
        OFFLINE_CACHE.invalidate(baseGamer.getName());
    }

    public boolean isOnline(int playerID) {
        return GAMERS.values().stream().anyMatch(baseGamer -> baseGamer.getPlayerID() == playerID);
    }

    public boolean isOnline(String name) {
        return GAMERS.containsKey(name);
    }

    public Map<IBaseGamer, Integer> getLevelTop(int limit) {
        return PlayerInfoLoader.getMySql().executeQuery("SELECT * FROM player_networking ORDER BY exp DESC LIMIT " + limit, rs -> {
            Map<IBaseGamer, Integer> levelTop = new LinkedHashMap<>();
            while (rs.next()) {
                if (rs.getInt(1) == -1) {
                    continue;
                }
                IBaseGamer baseGamer = getFromCacheOrLoad(rs.getInt(1));
                if (baseGamer == null) {
                    continue;
                }
                levelTop.put(baseGamer, baseGamer.getLevel());
            }
            return levelTop;
        });
    }

    public Map<IBaseGamer, Integer> getOnlineTop(int limit) {
        return PlayerInfoLoader.getMySql().executeQuery("SELECT * FROM player_networking ORDER BY play_time DESC LIMIT " + limit, rs -> {
            Map<IBaseGamer, Integer> onlineTop = new LinkedHashMap<>();
            while (rs.next()) {
                if (rs.getInt(1) == -1) {
                    continue;
                }
                IBaseGamer baseGamer = getFromCacheOrLoad(rs.getInt(1));
                if (baseGamer == null) {
                    continue;
                }
                onlineTop.put(baseGamer, baseGamer.isOnline() ? baseGamer.getPlayTime() : rs.getInt(5));
            }
            return onlineTop;
        });
    }
}
