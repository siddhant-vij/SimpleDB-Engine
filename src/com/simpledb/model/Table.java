package com.simpledb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class Table {
  private String name;
  private List<String> columns;
  // columns can be made as Map<String, String>
  // to support data types & validation functionality
  private List<Record> records;

  // Jackson requires a no-arg constructor for deserialization
  public Table() {
    columns = new ArrayList<>();
    records = new ArrayList<>();
  }

  @JsonCreator
  public Table(@JsonProperty("name") String name, @JsonProperty("columns") List<String> columns) {
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

  public void addRecord(Record record) throws RuntimeException {
    if (record.getFields().keySet().equals(new HashSet<>(columns))) {
      this.records.add(record);
    } else {
      throw new RuntimeException("Record fields do not match table columns.");
    }
  }

  public List<Record> getRecords() {
    return records;
  }
}
