package com.simpledb.core;

import com.simpledb.model.Table;
import com.simpledb.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
  private final Map<String, Table> tables = new HashMap<>();
  final String databasePath;

  public Database(String databasePath) {
    this.databasePath = databasePath;
  }

  public void createTable(String tableName, List<String> columns) throws RuntimeException {
    if (tables.containsKey(tableName)) {
      throw new RuntimeException("Table already exists: " + tableName);
    }
    Table newTable = new Table(tableName, columns);
    tables.put(tableName, newTable);
    try {
      FileUtils.writeTableToFile(newTable, databasePath + "/" + tableName + ".json");
    } catch (IOException e) {
      System.out.println("Error creating table: " + e.getMessage());
      tables.remove(tableName);
      System.exit(1);
    }
  }

  public void dropTable(String tableName) throws RuntimeException {
    if (!tables.containsKey(tableName)) {
      File tableFile = new File(databasePath + "/" + tableName + ".json");
      if (!tableFile.exists()) {
        throw new RuntimeException("Table does not exist: " + tableName);
      }
    }
    tables.remove(tableName);
    new File(databasePath + "/" + tableName + ".json").delete();
  }

  public Table getTable(String tableName) {
    String filePath = databasePath + "/" + tableName + ".json";
    try {
      return FileUtils.readTableFromFile(filePath);
    } catch (IOException e) {
      System.out.println("Error getting table: " + e.getMessage());
      return null;
    }
  }
}
