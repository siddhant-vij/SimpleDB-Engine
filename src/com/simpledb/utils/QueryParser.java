package com.simpledb.utils;

import com.simpledb.model.Record;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QueryParser {

  private static final Pattern CREATE_PATTERN = Pattern.compile("CREATE TABLE (\\w+) \\((.*)\\)");
  private static final Pattern INSERT_PATTERN = Pattern.compile("INSERT INTO (\\w+) \\((.*)\\) VALUES \\((.*)\\)");
  private static final Pattern SELECT_PATTERN = Pattern.compile("SELECT \\* FROM (\\w+)( WHERE (.*))?");
  private static final Pattern UPDATE_PATTERN = Pattern.compile("UPDATE (\\w+) SET (.*) WHERE (.*)");
  private static final Pattern DELETE_PATTERN = Pattern.compile("DELETE FROM (\\w+) WHERE (.*)");
  private static final Pattern DROP_PATTERN = Pattern.compile("DROP TABLE (\\w+)");

  public static ParsedQuery parse(String query) throws RuntimeException {
    String queryType = determineQueryType(query);
    Matcher matcher;

    switch (queryType) {
      case "CREATE":
        matcher = CREATE_PATTERN.matcher(query);
        matcher.find();
        return new ParsedQuery("CREATE", matcher.group(1), parseColumns(matcher.group(2)), null, null, null);

      case "INSERT":
        matcher = INSERT_PATTERN.matcher(query);
        matcher.find();
        return new ParsedQuery("INSERT", matcher.group(1), Arrays.asList(matcher.group(2).split(",\\s*")),
            Arrays.asList(matcher.group(3).split(",\\s*")), null, null);

      case "SELECT":
        matcher = SELECT_PATTERN.matcher(query);
        matcher.find();
        Predicate<Record> selectCondition = parseCondition(matcher.group(3));
        return new ParsedQuery("SELECT", matcher.group(1), null, null, selectCondition, null);

      case "UPDATE":
        matcher = UPDATE_PATTERN.matcher(query);
        matcher.find();
        Map<String, String> updateSet = parseUpdateSet(matcher.group(2));
        Predicate<Record> updateCondition = parseCondition(matcher.group(3));
        return new ParsedQuery("UPDATE", matcher.group(1), null, null, updateCondition, updateSet);

      case "DELETE":
        matcher = DELETE_PATTERN.matcher(query);
        matcher.find();
        Predicate<Record> deleteCondition = parseCondition(matcher.group(2));
        return new ParsedQuery("DELETE", matcher.group(1), null, null, deleteCondition, null);

      case "DROP":
        matcher = DROP_PATTERN.matcher(query);
        matcher.find();
        return new ParsedQuery("DROP", matcher.group(1), null, null, null, null);

      default:
        throw new RuntimeException("Unsupported query format.");
    }
  }

  private static String determineQueryType(String query) {
    if (CREATE_PATTERN.matcher(query).matches())
      return "CREATE";
    if (INSERT_PATTERN.matcher(query).matches())
      return "INSERT";
    if (SELECT_PATTERN.matcher(query).matches())
      return "SELECT";
    if (UPDATE_PATTERN.matcher(query).matches())
      return "UPDATE";
    if (DELETE_PATTERN.matcher(query).matches())
      return "DELETE";
    if (DROP_PATTERN.matcher(query).matches())
      return "DROP";
    return "UNKNOWN";
  }

  private static List<String> parseColumns(String columns) {
    return Arrays.asList(columns.split(",\\s*"));
  }

  private static Map<String, String> parseUpdateSet(String updateSet) {
    return Arrays.stream(updateSet.split(",\\s*"))
        .map(s -> s.split("=", 2))
        .collect(Collectors.toMap(a -> a[0], a -> a[1]));
  }

  // Future improvement: Enhance this method to support more complex conditions.
  private static Predicate<Record> parseCondition(String conditionStr) throws RuntimeException {
    if (conditionStr == null || conditionStr.trim().isEmpty()) {
      return record -> true; // All records match.
    }
    // Assuming a simple "field=value" condition.
    String[] parts = conditionStr.split("=");
    if (parts.length != 2) {
      throw new RuntimeException("Invalid condition format");
    }
    String field = parts[0].trim();
    String value = parts[1].trim();
    return record -> value.equals(record.getFields().get(field).toString());
  }

  public static class ParsedQuery {
    public final String type;
    public final String tableName;
    public final List<String> columns;
    public final List<String> values;
    public final Predicate<Record> condition;
    public final Map<String, String> updateSet;

    public ParsedQuery(String type, String tableName, List<String> columns, List<String> values,
        Predicate<Record> condition, Map<String, String> updateSet) {
      this.type = type;
      this.tableName = tableName;
      this.columns = columns;
      this.values = values;
      this.condition = condition;
      this.updateSet = updateSet;
    }
  }
}
