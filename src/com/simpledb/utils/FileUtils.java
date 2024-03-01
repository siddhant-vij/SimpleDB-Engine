package com.simpledb.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpledb.model.Table;

public class FileUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void writeTableToFile(Table table, String filePath) throws IOException {
    objectMapper.writeValue(new File(filePath), table);
  }

  public static Table readTableFromFile(String filePath) throws IOException {
    return objectMapper.readValue(new File(filePath), Table.class);
  }
}
