package com.simpledb.core;

import com.simpledb.model.Record;
import com.simpledb.utils.QueryParser;
import com.simpledb.utils.QueryParser.ParsedQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryProcessor {
  private final CRUDOperations crudOperations;

  public QueryProcessor(CRUDOperations crudOperations) {
    this.crudOperations = crudOperations;
  }

  public void processQuery(String queryString) throws RuntimeException {
    ParsedQuery query = QueryParser.parse(queryString);

    switch (query.type) {
      case "CREATE":
        handleCreate(query);
        break;
      case "INSERT":
        handleInsert(query);
        break;
      case "SELECT":
        handleSelect(query);
        break;
      case "UPDATE":
        handleUpdate(query);
        break;
      case "DELETE":
        handleDelete(query);
        break;
      case "DROP":
        handleDrop(query);
        break;
      default:
        throw new RuntimeException("Unsupported operation: " + query.type);
    }
  }

  private void handleCreate(ParsedQuery query) throws RuntimeException {
    List<String> columns = new ArrayList<>();
    for (String col : query.columns) {
      String[] parts = col.split("\\s+");
      columns.add(parts[0]);
      // parts[1] is the data type - ignoring now,
      // but this functionality can be added later
    }
    crudOperations.createTable(query.tableName, columns);
  }

  private void handleInsert(ParsedQuery query) throws RuntimeException {
    Map<String, Object> fields = new HashMap<>();
    for (int i = 0; i < query.columns.size(); i++) {
      fields.put(query.columns.get(i), query.values.get(i));
    }
    crudOperations.insertRecord(query.tableName, fields);
  }

  private void handleSelect(ParsedQuery query) throws RuntimeException {
    List<Record> results = crudOperations.selectRecords(query.tableName, query.condition);
    System.out.println("Selected records: ");
    for (Record record : results) {
      String recordStr = record
          .getFields()
          .entrySet()
          .stream()
          .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
          .collect(Collectors.joining(", "));
      System.out.println(recordStr);
    }
  }

  private void handleUpdate(ParsedQuery query) throws RuntimeException {
    Map<String, Object> updates = query.updateSet
        .entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    crudOperations.updateRecords(query.tableName, updates, query.condition);
  }

  private void handleDelete(ParsedQuery query) throws RuntimeException {
    crudOperations.deleteRecords(query.tableName, query.condition);
  }

  private void handleDrop(ParsedQuery query) throws RuntimeException {
    crudOperations.dropTable(query.tableName);
  }
}
