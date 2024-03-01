package com.simpledb.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
  private final String name;
  private final List<String> columns;
  // columns can be made as Map<String, String>
  // to support data types & validation functionality
  private final List<Record> records;

  public Table(String name, List<String> columns) {
    this.name = name;
    this.columns = new ArrayList<>(columns);
    this.records = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public List<String> getColumns() {
    return new ArrayList<>(columns);
  }

  public void addRecord(Record record) {
    this.records.add(record);
  }

  public List<Record> getRecords() {
    return new ArrayList<>(records);
  }
}
