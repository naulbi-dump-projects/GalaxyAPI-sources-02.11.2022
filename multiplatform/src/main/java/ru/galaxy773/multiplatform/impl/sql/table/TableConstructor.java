package ru.galaxy773.multiplatform.impl.sql.table;

import lombok.Getter;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class TableConstructor {

    @Getter
    private final String name;
    @Getter
    private final List<TableColumn> tableColumns;
    private final List<String> columns = new ArrayList<>();

    public TableConstructor(String name, TableColumn... tableColumns) {
        this.name = name;
        this.tableColumns = Arrays.asList(tableColumns);
    }
    
    public void addIndex(String column) {
        this.columns.add(column);
    }

    @Override
    public String toString() {
        String columnSql = tableColumns.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String primary = tableColumns.stream()
                .filter(TableColumn::isPrimaryKey)
                .map(TableColumn::getName)
                .collect(Collectors.joining(", "));

        if (!primary.isEmpty()) {
            columnSql = columnSql + ", PRIMARY KEY (" + primary + ")";
        }

        return "CREATE TABLE IF NOT EXISTS `" + name + "` (" + columnSql
                + ") ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;";
    }

    public void create(MySqlDatabase database) {
        database.execute(this.toString());

        for (String columnName : columns) {
            database.execute("ALTER TABLE `" + name + "` ADD INDEX (`" + columnName + "`);");
        }
    }
}
