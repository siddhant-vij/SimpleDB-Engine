package com.simpledb.model;

import java.util.HashMap;
import java.util.Map;

public class Record {
  private Map<String, Object> fields;

  private Record(Map<String, Object> fields) {
    this.fields = new HashMap<>(fields);
  }

  public static Record create(Map<String, Object> fields) {
    return new Record(fields);
  }

  public Map<String, Object> getFields() {
    return new HashMap<>(fields);
  }

  public void updateField(String fieldName, Object value) {
    this.fields.put(fieldName, value);
  }
}
