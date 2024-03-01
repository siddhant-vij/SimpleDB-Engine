package com.simpledb.core;

import com.simpledb.model.Record;
import com.simpledb.model.Table;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CRUDOperations {

  private final Database database;

  public CRUDOperations(Database database) {
    this.database = database;
  }

  public void createTable(String tableName, List<String> columns) {
    database.createTable(tableName, columns);
  }

  public void insertRecord(String tableName, Map<String, Object> fields) {
    Table table = database.getTable(tableName);
    if (table == null) {
      throw new IllegalArgumentException("Table " + tableName + " does not exist.");
    }
    Record record = Record.create(fields);
    table.addRecord(record);
  }

  public List<Record> selectRecords(String tableName, Predicate<Record> condition) {
    Table table = database.getTable(tableName);
    if (table == null) {
      throw new IllegalArgumentException("Table " + tableName + " does not exist.");
    }
    return table.getRecords()
        .stream()
        .filter(condition)
        .collect(Collectors.toList());
  }

  public void updateRecords(String tableName, Map<String, Object> updates, Predicate<Record> condition) {
    Table table = database.getTable(tableName);
    if (table == null) {
      throw new IllegalArgumentException("Table " + tableName + " does not exist.");
    }
    table.getRecords()
        .stream()
        .filter(condition)
        .forEach(record -> {
          updates.forEach((key, value) -> {
            if (table.getColumns().contains(key)) {
              record.updateField(key, value);
            } else {
              throw new IllegalArgumentException("Column " + key + " does not exist in table " + tableName);
            }
          });
        });
  }

  public void deleteRecords(String tableName, Predicate<Record> condition) {
    Table table = database.getTable(tableName);
    if (table == null) {
      throw new IllegalArgumentException("Table " + tableName + " does not exist.");
    }
    table.getRecords().removeIf(condition);
  }

  public void dropTable(String tableName) {
    database.dropTable(tableName);
  }
}
