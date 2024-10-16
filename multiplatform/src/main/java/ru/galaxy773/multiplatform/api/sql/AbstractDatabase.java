package ru.galaxy773.multiplatform.api.sql;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import ru.galaxy773.multiplatform.api.sql.query.Query;
import ru.galaxy773.multiplatform.api.sql.response.ResponseHandler;
import ru.galaxy773.multiplatform.impl.sql.StatementWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface AbstractDatabase {

    ExecutorService QUERY_EXECUTOR = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                    .setNameFormat("Hikari-Thread #%s")
                    .setDaemon(true)
                    .build());

    int execute(String query, Object... objects);

    int execute(Query query);

    int execute(StatementWrapper wrapper, Object... objects);

    <T> T executeQuery(String query, ResponseHandler<ResultSet, T> handler, Object... objects);

    <T> T executeQuery(Query query, ResponseHandler<ResultSet, T> handler);

    <T> T executeQuery(StatementWrapper wrapper, ResponseHandler<ResultSet, T> handler, Object... objects);

    void close();

    Connection getConnection();
}
