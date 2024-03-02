package com.simpledb.core;

import com.simpledb.model.Record;
import com.simpledb.model.Table;
import com.simpledb.utils.LockManager;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CRUDOperations {

  private final Database database;
  private final LockManager lockManager;

  public CRUDOperations(Database database, LockManager lockManager) {
    this.database = database;
    this.lockManager = lockManager;
  }

  public void createTable(String tableName, List<String> columns) throws RuntimeException {
    lockManager.acquireWriteLock(tableName);
    try {
      database.createTable(tableName, columns);
    } finally {
      lockManager.releaseWriteLock(tableName);
    }
  }

  public void insertRecord(String tableName, Map<String, Object> fields) throws RuntimeException {
    lockManager.acquireWriteLock(tableName);
    try {
      Table table = database.getTable(tableName);
      if (table == null) {
        throw new RuntimeException("Table " + tableName + " does not exist.");
      }
      Record record = Record.create(fields);
      table.addRecord(record);
    } finally {
      lockManager.releaseWriteLock(tableName);
    }
  }

  public List<Record> selectRecords(String tableName, Predicate<Record> condition) throws RuntimeException {
    lockManager.acquireReadLock(tableName);
    try {
      Table table = database.getTable(tableName);
      if (table == null) {
        throw new RuntimeException("Table " + tableName + " does not exist.");
      }
      return table.getRecords()
          .stream()
          .filter(condition)
          .collect(Collectors.toList());
    } finally {
      lockManager.releaseReadLock(tableName);
    }
  }

  public void updateRecords(String tableName, Map<String, Object> updates, Predicate<Record> condition)
      throws RuntimeException {
    lockManager.acquireWriteLock(tableName);
    try {
      Table table = database.getTable(tableName);
      if (table == null) {
        throw new RuntimeException("Table " + tableName + " does not exist.");
      }
      table.getRecords()
          .stream()
          .filter(condition)
          .forEach(record -> {
            updates.forEach((key, value) -> {
              if (table.getColumns().contains(key)) {
                record.updateField(key, value);
              } else {
                throw new RuntimeException("Column " + key + " does not exist in table " + tableName);
              }
            });
          });
    } finally {
      lockManager.releaseWriteLock(tableName);
    }
  }

  public void deleteRecords(String tableName, Predicate<Record> condition) throws RuntimeException {
    lockManager.acquireWriteLock(tableName);
    try {
      Table table = database.getTable(tableName);
      if (table == null) {
        throw new RuntimeException("Table " + tableName + " does not exist.");
      }
      table.getRecords().removeIf(condition);
    } finally {
      lockManager.releaseWriteLock(tableName);
    }
  }

  public void dropTable(String tableName) throws RuntimeException {
    lockManager.acquireWriteLock(tableName);
    try {
      database.dropTable(tableName);
    } finally {
      lockManager.releaseWriteLock(tableName);
    }
  }
}
