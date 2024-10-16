package ru.galaxy773.multiplatform.impl.sql;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Builder;
import lombok.Getter;
import ru.galaxy773.multiplatform.api.sql.AbstractDatabase;
import ru.galaxy773.multiplatform.api.sql.query.Query;
import ru.galaxy773.multiplatform.api.sql.response.ResponseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDatabase implements AbstractDatabase {

    public static final String HOST = "127.0.0.1";
    public static final String USER = "notexoff";
    public static final String PASSWORD = "WefwrdmTx7Nexl8q";

    @Getter
    private final String host;
    @Getter
    private final String password;
    @Getter
    private final String user;
    @Getter
    private final String database;

    @Getter
    private final HikariDataSource dataSource;
    private Connection connection;

    @Builder(builderMethodName = "builder", builderClassName = "MySqlBuilder", buildMethodName = "build")
    public MySqlDatabase(String host,
                         String password,
                         String user,
                         String database) {

        this.host = host;
        this.password = password;
        this.user = user;
        this.database = database;
        this.dataSource = configureDataSource(new HikariDataSource());
    }

    private HikariDataSource configureDataSource(HikariDataSource hikariSource) {
        hikariSource.setPoolName("Hikari MySql-Pool");
        hikariSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariSource.setMaximumPoolSize(10);
        hikariSource.setMinimumIdle(1);
        hikariSource.addDataSourceProperty("serverName", this.host);
        hikariSource.addDataSourceProperty("user", this.user);
        hikariSource.addDataSourceProperty("password", this.password);
        hikariSource.addDataSourceProperty("databaseName", this.database);
        hikariSource.addDataSourceProperty("port", 3306);
        hikariSource.addDataSourceProperty("useUnicode", "true");
        hikariSource.addDataSourceProperty("characterEncoding", "utf8");
        hikariSource.addDataSourceProperty("cachePrepStmts", true);
        hikariSource.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariSource.addDataSourceProperty("leakDetectionThreshold", 6000);
        hikariSource.addDataSourceProperty("useSSL", false);
        hikariSource.addDataSourceProperty("requireSSL", false);
        hikariSource.addDataSourceProperty("verifyServerCertificate", false);

        return hikariSource;
    }

    @Override
    public int execute(Query query) {
        return execute(query.toString());
    }

    @Override
    public <T> T executeQuery(Query query, ResponseHandler<ResultSet, T> handler) {
        return executeQuery(query.toString(), handler);
    }

    @Override
    public int execute(String query, Object... objects) {
        return execute(StatementWrapper.create(this, query), objects);
    }

    @Override
    public <T> T executeQuery(String query, ResponseHandler<ResultSet, T> handler, Object... objects) {
        return executeQuery(StatementWrapper.create(this, query), handler, objects);
    }

    @Override
    public int execute(StatementWrapper wrapper, Object... objects) {
        return wrapper.execute(PreparedStatement.RETURN_GENERATED_KEYS, objects);
    }

    @Override
    public <T> T executeQuery(StatementWrapper wrapper, ResponseHandler<ResultSet, T> handler, Object... objects) {
        return wrapper.executeQuery(handler, objects);
    }

    @Override
    public Connection getConnection() {
        refreshConnection();
        return connection;
    }

    private void refreshConnection() {
        try {
            if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
                return;
            }
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Проблема соединения для: " + this.host + "/" + this.database, e);
        }
    }

    @Override
    public void close() {
        try {
            this.connection.close();
            this.dataSource.close();
        } catch (SQLException ignored) {}
    }
}
