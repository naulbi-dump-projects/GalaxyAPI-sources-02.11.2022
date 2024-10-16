package ru.galaxy773.multiplatform.api.redis;

import redis.clients.jedis.Jedis;
import ru.galaxy773.multiplatform.api.utils.builders.Builder;

public class RedisBuilder implements Builder<Jedis> {

    private String host = "localhost";
    private int port = 6379;
    private int connectionTimeout = 2000;
    private int soTimeout = 2000;
    private boolean ssl;

    public RedisBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public RedisBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public RedisBuilder setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public RedisBuilder setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        return this;
    }

    public RedisBuilder setSSL(boolean ssl) {
        this.ssl = ssl;
        return this;
    }

    @Override
    public Jedis build() {
        return new Jedis(this.host, this.port, this.connectionTimeout, this.soTimeout, this.ssl);
    }
}
