package com.simpledb.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Record {

  private final Map<String, Object> fields;

  private Record(Map<String, Object> fields) {
    this.fields = Collections.unmodifiableMap(new HashMap<>(fields));
  }

  public static Record create(Map<String, Object> fields) {
    return new Record(fields);
  }

  public Map<String, Object> getFields() {
    return fields;
  }
}
