package com.simpledb;

import com.simpledb.cli.CommandLineInterface;
import com.simpledb.core.CRUDOperations;
import com.simpledb.core.Database;
import com.simpledb.core.QueryProcessor;
import com.simpledb.utils.LockManager;

public class Main {
    public static void main(String[] args) {
        String databasePath = "e:/Java Projects Practice/SimpleDB Engine/resources";
        CommandLineInterface commandLineInterface = new CommandLineInterface(
                new QueryProcessor(new CRUDOperations(
                        new Database(databasePath), new LockManager())));
        commandLineInterface.start();
    }
}
