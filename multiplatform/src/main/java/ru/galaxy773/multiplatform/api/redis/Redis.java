package ru.galaxy773.multiplatform.api.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Redis {

    private final Type REDIS_TYPE = new TypeToken<Map<String, JedisData>>() {}.getType();
    @Setter
    private static File redisResourse;

    public Jedis getJedis(String redisName) {
        if (!redisResourse.exists()) {
            throw new RuntimeException("Redis json file not created");
        }
        Map<String, JedisData> jedisData = new HashMap<>();
        Gson gson = new Gson();
        try {
            jedisData = gson.fromJson(gson.newJsonReader(new FileReader(redisResourse)), REDIS_TYPE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!jedisData.containsKey(redisName)) {
            throw new IllegalArgumentException("Redis data for " + redisName + " not found in redis.json");
        }

        JedisData jedis = jedisData.get(redisName);
        return new Jedis(jedis.getHost(), jedis.getPort(), jedis.getConnectionTimeout(), jedis.getSoTimeout(), jedis.isSsl());
    }

    @AllArgsConstructor
    @Getter
    private static final class JedisData {

        private final String host;
        private final int port;
        private final int connectionTimeout;
        private final int soTimeout;
        private final boolean ssl;
    }
}
