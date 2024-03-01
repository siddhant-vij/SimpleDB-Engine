package com.simpledb.core;

import com.simpledb.model.Table;
import com.simpledb.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Database {
  private final Map<String, Table> tables = new HashMap<>();
  private final String databasePath;

  private Database(String databasePath) {
    this.databasePath = databasePath;
  }

  public static Database createDatabase(String databasePath) {
    return new Database(databasePath);
  }

  public void createTable(String tableName, Map<String, String> columns) throws IOException {
    Table newTable = Table.create(tableName, columns);
    tables.put(tableName, newTable);
    FileUtils.writeTableToFile(newTable, databasePath + "/" + tableName + ".json");
  }

  public void dropTable(String tableName) {
    tables.remove(tableName);
    new File(databasePath + "/" + tableName + ".json").delete();
  }

  public Map<String, Table> getTables() {
    return Collections.unmodifiableMap(tables);
  }
}
