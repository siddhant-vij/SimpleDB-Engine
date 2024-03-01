package com.simpledb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Table {
  private final String name;
  private final Map<String, String> columns;
  private final List<Record> records;

  private Table(String name, Map<String, String> columns, List<Record> records) {
    this.name = name;
    this.columns = Collections.unmodifiableMap(new HashMap<>(columns));
    this.records = Collections.unmodifiableList(new ArrayList<>(records));
  }

  public static Table create(String name, Map<String, String> columns) {
    return new Table(name, columns, new ArrayList<>());
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getColumns() {
    return columns;
  }

  public List<Record> getRecords() {
    return records;
  }

  public void addRecord(Record record) {
    records.add(record);
  }
}
