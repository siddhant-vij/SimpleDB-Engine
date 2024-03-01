package com.simpledb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpledb.model.Table;

import java.io.File;
import java.io.IOException;

public class FileUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private FileUtils() {
  }

  public static void writeTableToFile(Table table, String filePath) throws IOException {
    objectMapper.writeValue(new File(filePath), table);
  }

  public static Table readTableFromFile(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), Table.class);
  }
}
