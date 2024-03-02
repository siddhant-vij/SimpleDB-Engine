package com.simpledb.cli;

import com.simpledb.core.QueryProcessor;

import java.util.Scanner;

public class CommandLineInterface {
  private final QueryProcessor queryProcessor;

  public CommandLineInterface(QueryProcessor queryProcessor) {
    this.queryProcessor = queryProcessor;
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);
    String userInput;

    System.out.println("Welcome to SimpleDB Engine CLI.");
    printHelp();

    while (true) {
      System.out.print("\n> ");
      userInput = scanner.nextLine();

      if ("exit".equalsIgnoreCase(userInput)) {
        System.out.println("Exiting SimpleDB Engine CLI.");
        break;
      } else if ("help".equalsIgnoreCase(userInput)) {
        printHelp();
        continue;
      }

      try {
        queryProcessor.processQuery(userInput);
        System.out.println("Command executed successfully.");
      } catch (RuntimeException e) {
        System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
      }
    }

    scanner.close();
  }

  private void printHelp() {
    System.out.println("\nAvailable commands:");
    System.out.println("CREATE TABLE <tableName> (<column1> <type1>, ...)");
    System.out.println("INSERT INTO <tableName> (<column1>, ...) VALUES (<value1>, ...)");
    System.out.println("SELECT * FROM <tableName> [WHERE <condition>]");
    System.out.println("UPDATE <tableName> SET <column1>=<value1>, ... [WHERE <condition>]");
    System.out.println("DELETE FROM <tableName> [WHERE <condition>]");
    System.out.println("DROP TABLE <tableName>");
    System.out.println("help - Show this help menu");
    System.out.println("exit - Exit the CLI");
  }
}
